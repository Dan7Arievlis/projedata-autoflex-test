import { ChangeDetectorRef, Component, Input } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../product-service';
import { InputService } from '../../inputs/input-service';
import { Router } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Product } from '../product-model';
import { MatTableDataSource } from '@angular/material/table';
import { V } from '@angular/cdk/keycodes';
import { debounceTime, distinctUntilChanged, of, switchMap } from 'rxjs';
import { AuthService } from '../../auth/auth-service';

@Component({
  selector: 'app-product-list-component',
  standalone: false,
  templateUrl: './product-list-component.html',
  styleUrl: './product-list-component.scss',
})
export class ProductListComponent {
  isAdmin!: boolean;
  form: FormGroup;
  inputsForm: FormGroup;
  searchBar: FormGroup;

  products = new MatTableDataSource<Product>();
  inputsFromDb: any[] = [];
  requiredInputs: any[] = [];

  selectedInputObject: any;
  filteredInputs: any[] = [];

  displayedColumns = ['name', 'value', 'maxProduction', 'totalValue', 'actions'];
  totalElements = 0;
  pageSize = 5;
  pageIndex = 0;

  constructor(
    private service: ProductService,
    private inputService: InputService,
    private router: Router,
    private snack: MatSnackBar,
    private cd: ChangeDetectorRef,
    private auth: AuthService
  ) {
    this.form = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.minLength(2)]),
      value: new FormControl(null, [Validators.required])
    });
    
    this.inputsForm = new FormGroup({
      selectedInput: new FormControl('', [Validators.required]),
      inputAmount: new FormControl(null, [Validators.required, Validators.pattern(/^\d+([.,]\d+)?$/), Validators.min(0.001)])
    });

    this.searchBar = new FormGroup({
      search: new FormControl('', [])
    });
  }

  loadProducts() {
    this.service.search(this.searchBar.value['search'], this.pageIndex, this.pageSize).subscribe(response => {
      this.products = response.content;
      this.totalElements = response.totalElements;
      this.cd.detectChanges();
    });
  }

  loadInputs() {
    this.inputService.search(this.selectedInputObject).subscribe(response => {
      this.inputsFromDb = response.content;
    });
  }

  addInput() {
    const amount = this.inputsForm.value.inputAmount;
    this.inputsForm.markAllAsTouched();
    if (!this.selectedInputObject || amount <= 0 || this.inputsForm.invalid)
      return

    this.requiredInputs.push({
      id: this.selectedInputObject.id,
      name: this.selectedInputObject.name,
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

  submit() {
    this.form.markAllAsTouched();
    if (this.form.invalid || this.requiredInputs.length === 0)
      return;
    
    const rawValue = this.form.value.value
      .replace('R$ ', '')
      .replace(/\./g, '')
      .replace(',', '.');

    const payload = {
      name: this.form.value.name,
      value: parseFloat(rawValue),
      inputs: this.requiredInputs.map(input => ({
        id: input.id,
        amount: input.amount
      }))
    };

    this.service.create(payload).subscribe({
      next: () => {
        this.requiredInputs = [];
        this.form.reset();
        this.showSnack('Produto cadastrado com sucesso!');
        this.loadProducts();
      },
      error: error => {
        this.showSnack(`Falha no cadastro de produto!`)
      }
    });
  }

  edit(id: string) {
    this.router.navigate(['/pages/products', id]);
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
    this.filteredInputs = this.inputsFromDb.filter(input => input.name.toLowerCase().includes(filterValue));
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

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadProducts();
  }

  ngOnInit() {
    this.isAdmin = this.auth.isAdmin();
    this.loadProducts();
    this.loadInputs();

    if (!this.isAdmin) {
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
            !this.requiredInputs.some(req => req.id === input.id)
          );

          this.cd.detectChanges();
      });

    this.searchBar.get('search')?.valueChanges
      .pipe(
        debounceTime(400),
        distinctUntilChanged()
      )
      .subscribe(() => {
        this.pageIndex = 0;
        this.loadProducts();
      });
  }
}