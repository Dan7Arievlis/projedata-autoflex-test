import { HttpClient } from '@angular/common/http';
import { Component, signal } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../auth-service';

@Component({
  selector: 'app-register-component',
  standalone: false,
  templateUrl: './register-component.html',
  styleUrl: './register-component.scss',
})
export class RegisterComponent {

  form: FormGroup;
  hide = signal(true);

  constructor(
    private router: Router,
    private auth: AuthService
  ){
    this.form = new FormGroup({
      login: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(8)]),
      confirmPassword: new FormControl('', [Validators.required]),
      roles: new FormControl(['USER'])
    }, {
      validators: passwordMatchValidator()
    });
  }

  register() {
    this.form.markAllAsTouched();
    if (this.form.invalid) return;

    this.auth.register(this.form).subscribe(() => {
      this.form.reset()
      this.router.navigate(['/auth/login']);
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

  toggleHide() {
    this.hide.set(!this.hide());
  }
}

function passwordMatchValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {

    const password = control.get('password')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;

    if (!password || !confirmPassword) 
      return null

    return password === confirmPassword ? null : { passwordMismatch: true };
  };
}