import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  http = inject(HttpClient);
  router = inject(Router);

  readonly jwtInfo = signal<JwtInfo | null>(null);

  redirectUrl: string | null = null;

  constructor() {
    this.decodeJwt();
  }

  login(credentials: { email: string; password: string }) {
    return this.http
      .post(`${environment.serverUrl}/log-in`, credentials, { responseType: 'text' })
      .pipe(
        tap((jwt) => {
          localStorage.setItem('jwt', jwt);
          this.decodeJwt();

          const theme = localStorage.getItem(`theme_${this.jwtInfo()?.id}`) ?? "default";
          document.documentElement.setAttribute('data-theme', theme);
        }),
      );
  }

  decodeJwt() {
    const jwt = localStorage.getItem('jwt');

    if (jwt) {
      const jwtParts = jwt.split('.');
      const bodyBase64 = jwtParts[1];
      const bodyJson = atob(bodyBase64);
      const body = JSON.parse(bodyJson);

      this.jwtInfo.set(body);
    }
  }

  logout() {
    localStorage.removeItem('jwt');
    this.jwtInfo.set(null);
    this.router.navigate(['/']);
  }
}
