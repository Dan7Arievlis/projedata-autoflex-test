import { ChangeDetectorRef, Component, Input } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../product-service';
import { InputService } from '../../inputs/input-service';
import { ActivatedRoute, Router } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Product } from '../product-model';
import { MatTableDataSource } from '@angular/material/table';
import { V } from '@angular/cdk/keycodes';
import { debounceTime, distinctUntilChanged, of, switchMap } from 'rxjs';
import { AuthService } from '../../auth/auth-service';

@Component({
  selector: 'app-product-details-component',
  standalone: false,
  templateUrl: './product-details-component.html',
  styleUrl: './product-details-component.scss',
})
export class ProductDetailsComponent {
  form: FormGroup;
  inputsForm: FormGroup;

  id!: string;
  requiredInputs: any[] = [];
  maxProduction!: number;
  totalValue!: number;

  selectedInputObject: any;
  filteredInputs: any[] = [];

  constructor(
    private service: ProductService,
    private inputService: InputService,
    private router: Router,
    private route: ActivatedRoute,
    private auth: AuthService,
    private snack: MatSnackBar,
    private cd: ChangeDetectorRef
  ) {
    this.form = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.minLength(2)]),
      value: new FormControl(null, [Validators.required])
    });
    
    this.inputsForm = new FormGroup({
      selectedInput: new FormControl('', Validators.required),
      inputAmount: new FormControl(null, [Validators.required, Validators.pattern(/^\d+([.,]\d+)?$/), Validators.min(0.001)])
    });
  }

  loadProduct() {
    this.service.findById(this.id).subscribe({
      next: product => {
        this.form.patchValue({
          name: product.name,
          value: this.formatToCurrency(product.value),
          inputs: product.inputs
        });
       
        this.maxProduction = product.maxProduction;
        this.totalValue = product.totalValue;
      }
    });
    this.cd.detectChanges();
  }

  loadInputs() {
    this.service.findInputs(this.id).subscribe({
      next: list => {
        this.requiredInputs = list;
        this.cd.detectChanges();
      }
    });
  }

  addInput() {
    const amount = this.inputsForm.value.inputAmount;
    this.inputsForm.markAllAsTouched();
    if (!this.selectedInputObject || amount <= 0 || this.inputsForm.invalid)
      return

    this.requiredInputs.push({
      inputId: this.selectedInputObject.id,
      inputName: this.selectedInputObject.name,
      totalAmount: this.selectedInputObject.amount,
      productId: this.id,
      amount: amount
    });

    this.inputsForm.patchValue({
      selectedInput: '',
      inputAmount: null
    });

    this.selectedInputObject = null;
    this.filterInputs(this.inputsForm.get('selectedInput')?.value);
  }

  removeInput(index: number) {
    this.requiredInputs.splice(index, 1);
    this.filterInputs(this.inputsForm.get('selectedInput')?.value);
  }

  update() {
    this.form.markAllAsTouched();
    if (this.form.invalid || this.requiredInputs.length === 0)
      return;    
    
    const payload = {
      name: this.form.value.name,
      value: this.parseCurrency(this.form.value.value),
      inputs: this.requiredInputs.map(input => ({
        id: input.inputId,
        name: input.inputName,
        amount: input.amount
      }))
    };

    this.service.update(this.id, payload).subscribe({
      next: () => {
        this.loadProduct();
        this.form.reset();
        this.showSnack('Produto atualizado com sucesso!');
      },
      error: () => {
        this.showSnack(`Falha na atualização de produto!`)
      }
    });
  }

  parseCurrency(value: string): number {
    return Number(
      value
        .replace('R$', '')
        .replace(/\./g, '')
        .replace(',', '.')
        .trim()
    );
  }

  delete() {
    if (!confirm('Deseja realmente excluir?')) return;
    
    this.service.delete(this.id)
      .subscribe(() => {
        this.router.navigate(['/pages/products']);
        this.showSnack('Produto deletado com sucesso!')
      });
  }

  onInputSelected(input: any) {
    this.selectedInputObject = input;
  }

  filterInputs(value: any) {
    if (!value) {
      this.filteredInputs = [];
      return;
    }
    
    const filterValue = typeof value === 'string'
        ? value.toLowerCase()
        : value.name?.toLowerCase() || '';
    
    this.filteredInputs = this.requiredInputs.filter(input => input.inputName.toLowerCase().includes(filterValue));
  }

  displayInput(input: any): string {
    return input?.name || '';
  }

  showSnack(message: string) {
    this.snack.open(message, "Ok")
  }

  formatCurrency(event: any) {
    let value = event.target.value;
    value = value.replace(/\D/g, '');
    value = (Number(value) / 100).toFixed(2);
    value = value.replace('.', ',');
    value = value.replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    event.target.value = 'R$ ' + value;

    this.form.get('value')?.setValue(event.target.value, { emitEvent: false });
  }

  formatToCurrency(value: number | null | undefined): string {
    if (value == null) return '';

    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(value);
  }

  back() {
    this.router.navigate(['/pages/products'])
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id')!;
    this.loadProduct();
    this.loadInputs();

    if(!this.auth.isAdmin()) {
      this.form.disable();
      this.inputsForm.disable();
    }

    this.inputsForm.get('selectedInput')?.valueChanges.subscribe(value => {
      this.filterInputs(value);
    });

    this.inputsForm.get('selectedInput')?.valueChanges
      .pipe(
        debounceTime(200),
        distinctUntilChanged(),
        switchMap(value => {
          if (!value || typeof value !== 'string') {
            return of({ content: [] });
          }

          return this.inputService.search(value, 0, 10);
        })
      )
      .subscribe(response => {
        this.filteredInputs = response.content
          .filter((input: { id: any }) =>
            !this.requiredInputs.some(req => req.inputId === input.id)
          );

          this.cd.detectChanges();
      });
  }
}