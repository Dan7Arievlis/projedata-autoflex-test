import { inject, Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { FormGroup } from '@angular/forms';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private router = inject(Router);
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/auth';

  profile = signal<any>(null);

  constructor() {
    this.loadUserFromStorage();
  }

  login(login: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, {
      login,
      password
    }).pipe(
      tap(response => {
        this.setSession(response.token);
      })
    );
  }

  loginWithGoogle() {
    window.location.href ='http://localhost:8080/oauth2/authorization/google';
  }

  register(form: FormGroup): Observable<any> {
    return this.http.post('http://localhost:8080/users', form.value);
  }

  logout() {
    localStorage.removeItem('token');
    this.profile.set(null);
    localStorage.removeItem('token')
    this.router.navigate(['/auth/login']);
  }

  private setSession(token: string) {
    localStorage.setItem('token', token);

    const decoded: any = jwtDecode(token);
    this.profile.set(decoded);
  }

  private loadUserFromStorage() {
    const token = localStorage.getItem('token');

    if (token) {
      try {
        const decoded: any = jwtDecode(token);
        this.profile.set(decoded);
      } catch {
        this.logout();
      }
    }
  }

  get token(): string | null {
    return localStorage.getItem('token');
  }

  getLoggedProfile() {
    return this.profile();
  }

  getAuthorities(): string[] {
    const token = localStorage.getItem('token');
    if (!token) return [];

    const decoded: any = jwtDecode(token);
    return decoded.authorities || [];
  }

  isAdmin(): boolean {
    return this.getAuthorities().includes('ADMIN');
  }

  isUser(): boolean {
    return this.getAuthorities().includes('USER');
  }

  isAuthenticated(): boolean {
    if (!this.token) return false;

    const payload = JSON.parse(atob(this.token.split('.')[1]));
    const exp = payload.exp * 1000;

    return Date.now() < exp;
  }
}
