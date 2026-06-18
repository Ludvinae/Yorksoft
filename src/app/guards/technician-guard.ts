import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/business/auth-service';

export const technicianGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.jwtInfo() == null) {
    return router.parseUrl('/');
  }

  if (
    authService.jwtInfo()?.role != 'TECHNICIAN' &&
    authService.jwtInfo()?.role != 'ADMIN' &&
    authService.jwtInfo()?.role != 'MANAGER'
  ) {
    return router.parseUrl('/');
  }

  return true;
};
