import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/business/auth-service';

export const clientGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.jwtInfo() == null) {
    return router.parseUrl('/');
  }

  if (
    authService.jwtInfo()?.role != 'CLIENT' &&
    authService.jwtInfo()?.role != 'ADMIN' &&
    authService.jwtInfo()?.role == 'TECHNICIAN' &&
    authService.jwtInfo()?.role == 'DIRECTOR' &&
    authService.jwtInfo()?.role == 'MANAGER'
  ) {
    return router.parseUrl('/forbidden');
  }

  return true;
};
