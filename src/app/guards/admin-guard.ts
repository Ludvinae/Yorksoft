import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/business/auth-service';
import { inject } from '@angular/core';

export const adminGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  console.log(authService.jwtInfo()?.role);
  if (authService.jwtInfo() == null) {
    return router.parseUrl('/');
  }

  if (
    authService.jwtInfo()?.role != 'ADMIN'
  ) {
    return router.parseUrl('/forbidden');
  }

  return true;
};
