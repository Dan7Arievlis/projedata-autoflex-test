import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../auth/auth-service';
import { inject } from '@angular/core';

export const guestGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isAuthenticated()) {
    router.navigate(['/pages/inputs']);
    return false;
  }

  return true;
};