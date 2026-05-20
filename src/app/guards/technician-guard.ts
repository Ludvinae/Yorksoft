import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth-service';

export const technicianGuard: CanActivateFn = (route, state) => {

  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.jwtInfo() == null) {
    return router.parseUrl('/login');
  }

  if (
    authService.jwtInfo()?.role != 'technician' &&
    authService.jwtInfo()?.role != 'admin' &&
    authService.jwtInfo()?.role != 'manager'
  ) {
    return router.parseUrl('/forbidden');
  }

  return true;
};
