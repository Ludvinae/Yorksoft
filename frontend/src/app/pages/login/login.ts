import { Component, inject, OnInit, signal } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../../services/business/auth-service';
import { CenteredPage } from '../../components/layout/centered-page/centered-page';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, CenteredPage],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login implements OnInit {
  formBuilder = inject(FormBuilder);
  authService = inject(AuthService);
  router = inject(Router);

  form = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]],
  });

  // Public demo: shown only when the backend allows trial accounts
  readonly demo = signal<DemoStatus | null>(null);
  readonly startingDemo = signal(false);

  ngOnInit(): void {
    this.authService.getDemoStatus().subscribe({
      next: (status) => this.demo.set(status.enabled ? status : null),
      error: () => this.demo.set(null),
    });
  }

  onStartDemo(): void {
    this.startingDemo.set(true);
    this.authService.startDemo().subscribe({
      next: () => this.router.navigate(['/dashboard']),
      error: (err: HttpErrorResponse) => {
        this.startingDemo.set(false);
        if (err.status === 429) {
          const retryAfter = Number(err.headers.get('Retry-After'));
          const wait = retryAfter ? ` Réessayez dans ${Math.ceil(retryAfter / 60)} min.` : '';
          alert(`Trop de comptes d'essai créés depuis votre connexion.${wait}`);
        } else {
          alert("Impossible de créer un compte d'essai pour le moment, réessayez plus tard.");
        }
      },
    });
  }

  onLogin(): void {
    if (this.form.valid) {
      this.authService.login(this.form.value as { email: string; password: string }).subscribe({
        next: (jwt) => {
          // this.notification.open("connexion réussie", "valid");
          if (this.authService.redirectUrl) {
            const redirectUrl = this.authService.redirectUrl || '/';

            this.authService.redirectUrl = null;

            this.router.navigateByUrl(redirectUrl);
          } else {
            this.router.navigate(['/dashboard']);
          }
        },
        error: (err: HttpErrorResponse) => {
          if (err.status === 429) {
            const retryAfter = err.headers.get('Retry-After');
            const wait = retryAfter ? ` Réessayez dans ${retryAfter}s.` : '';
            alert(`Trop de tentatives de connexion.${wait}`);
          } else {
            // this.notification.open("Mauvais login / mot de passe", "error")
            alert('Mauvais login / mot de passe');
          }
        },
      });
    }
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.form.get(fieldName);

    return !!(field && field.invalid && (field.dirty || field.touched));
  }
}
