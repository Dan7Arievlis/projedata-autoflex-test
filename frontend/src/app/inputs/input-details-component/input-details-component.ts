import { ChangeDetectorRef, Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { InputService } from '../input-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PageEvent } from '@angular/material/paginator';
import { Product } from '../../products/product-model';
import { AuthService } from '../../auth/auth-service';

@Component({
  selector: 'app-input-details-component',
  standalone: false,
  templateUrl: './input-details-component.html',
  styleUrl: './input-details-component.scss',
})
export class InputDetailsComponent {
  form: FormGroup;
  id!: string;
  products: Product[] = [];
  loading: boolean = false;

  displayedColumns = ['name', 'actions']

  totalElements = 0;
  pageSize = 5;
  pageIndex = 0;

  constructor(
    private route: ActivatedRoute,
    private service: InputService,
    private router: Router,
    private snack: MatSnackBar,
    private cd: ChangeDetectorRef,
    private auth: AuthService
  ) {
    this.form = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.minLength(2)]),
      amount: new FormControl(0, [Validators.required, Validators.min(0)])
    });
  }

  loadInput() {
    this.service.findById(this.id).subscribe({
      next: input => {
        this.form.patchValue({
          name: input.name,
          amount: input.amount
        });
      }
    });
  }

  loadProducts() {
    this.service.findProducts(this.id).subscribe({
      next: list => {
        this.products = list;
        this.cd.detectChanges();
      }
    });
  }

  update() {
    if (this.form.invalid) return;

    this.service.update(this.id, this.form.value)
      .subscribe(() => this.showSnack('Insumo atualizado com sucesso!'));
  }

  delete() {
    if (!confirm('Deseja realmente excluir?')) return;
    
    this.service.delete(this.id)
    .subscribe({
      next: () => {
        this.showSnack('Insumo deletado com sucesso!')
        this.router.navigate(['/pages/inputs']);
      },
      error: () => this.showSnack('Impossível deletar insumos associados a produtos!')
    });
  }

  peek(id: string) {
    this.router.navigate(['/pages/products', id]);
  }

  showSnack(message: string) {
    this.snack.open(message, "Ok")
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadProducts();
  }

  back() {
    this.router.navigate(['/pages/inputs'])
  }

  ngOnInit() {
    if(!this.auth.isAdmin())
      this.form.disable()
    this.id = this.route.snapshot.paramMap.get('id')!;
    this.loadInput();
    this.loadProducts();
  }
}
