import { Component, signal } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../auth-service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login-component',
  standalone: false,
  templateUrl: './login-component.html',
  styleUrl: './login-component.scss',
})
export class LoginComponent {
  loading = false;
  error: string | null = null;

  form: FormGroup;
  hide = signal(true);

  constructor(
    private auth: AuthService,
    private router: Router,
    private snack: MatSnackBar
  ){
    this.form = new FormGroup({
      login: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required, Validators.minLength(8)])
    })
  }

  login() {
    this.form.markAllAsTouched();
    if (this.form.invalid)
      return

    this.loading = true;
    const { login, password } = this.form.value;    
    this.auth.login(login!, password!).subscribe({
      next: () => {
        this.form.reset();
        this.router.navigate(['/pages/inputs'])
      },
      error: () => {
        this.showSnack('Usuário ou senha inválidos')
        this.loading = false;
      }
    });
  }

  loginWithGoogle() {
    this.auth.loginWithGoogle();
  }

  isInvalidField(fieldName: string, constraint: string): boolean {
    const field = this.form.get(fieldName);
    
    return field?.invalid && field.touched && field.errors?.[constraint] || false;
  }
  
  isMinLengthInvalid(fieldName: string): boolean {
    const field = this.form.get(fieldName);
    return !!field?.errors?.['minlength'] && field.touched;
  }

  showSnack(message: string) {
    this.snack.open(message, "Entendi")
  }

  toggleHide() {
    this.hide.set(!this.hide());
  }
}
