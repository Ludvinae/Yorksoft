import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth-service';

export const clientGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.jwtInfo() == null) {
    return router.parseUrl('/login');
  }

  if (
    authService.jwtInfo()?.role != 'CLIENT' &&
    authService.jwtInfo()?.role != 'ADMIN'
  ) {
    return router.parseUrl('/forbidden');
  }

  return true;
};
