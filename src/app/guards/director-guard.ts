import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/business/auth-service';

export const directorGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.jwtInfo() == null) {
    return router.parseUrl('/');
  }

  if (authService.jwtInfo()?.role != 'DIRECTOR' && authService.jwtInfo()?.role != 'ADMIN') {
    return router.parseUrl('/');
  }

  return true;
};
