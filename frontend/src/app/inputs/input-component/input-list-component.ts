import { ChangeDetectorRef, Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { InputService } from '../input-service';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Input } from '../input-model';
import { AuthService } from '../../auth/auth-service';

@Component({
  selector: 'app-input-component',
  standalone: false,
  templateUrl: './input-list-component.html',
  styleUrl: './input-list-component.css',
})
export class InputComponent {
  isAdmin!: boolean;

  form: FormGroup;
  searchBar: FormGroup;
  dataSource = new MatTableDataSource<Input>();

  displayedColumns = ['name','amount','actions']
  totalElements = 0;
  pageSize = 5;
  pageIndex = 0;

  constructor(
    private service: InputService,
    private router: Router,
    private cd: ChangeDetectorRef,
    private snack: MatSnackBar,
    private auth: AuthService
  ) {
    this.form = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.minLength(2)]),
      amount: new FormControl(0, [Validators.required, Validators.min(0)])
    });

    this.searchBar = new FormGroup({
      search: new FormControl('', [])
    });
  }

  loadInputs() {
    this.service.search(this.searchBar.value['search'], this.pageIndex, this.pageSize).subscribe(result => {
      this.dataSource.data = result.content;
      this.totalElements = result.totalElements;
      this.cd.detectChanges();
    });
  }
  
  submit() {
    this.form.markAllAsTouched();
    if (this.form.invalid) return;
    
    this.service.create(this.form.value).subscribe({
      next: () => {
        this.loadInputs();
        this.form.reset();
        this.showSnack('Insumo adicionado com sucesso!')
      },
      error: () => {
        this.showSnack('Falha no cadastro de insumo! Registro duplicado')
      }
    });
  }
  
  edit(id: string) {
    this.router.navigate(['/pages/inputs', id]);
  }
  
  delete(id: string) {
    this.service.delete(id).subscribe(() => this.loadInputs());
  }

  showSnack(message: string) {
    this.snack.open(message, "Ok")
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadInputs();
  }

  ngOnInit() {
    this.isAdmin = this.auth.isAdmin();
    this.loadInputs();

    this.searchBar.get('search')?.valueChanges
      .pipe(
        debounceTime(400),
        distinctUntilChanged()
      )
      .subscribe(() => {
        this.pageIndex = 0;
        this.loadInputs();
      });
  }
}
