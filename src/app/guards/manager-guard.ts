import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth-service';

export const managerGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.jwtInfo() == null) {
    return router.parseUrl('/login');
  }

  if (authService.jwtInfo()?.role != 'manager' && authService.jwtInfo()?.role != 'admin') {
    return router.parseUrl('/forbidden');
  }

  return true;
};
