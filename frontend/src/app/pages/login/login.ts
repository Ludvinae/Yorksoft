import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/business/auth-service';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  formBuilder = inject(FormBuilder);
  authService = inject(AuthService);
  router = inject(Router);

  form = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]],
  });

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
        error: (err) => {
          // this.notification.open("Mauvais login / mot de passe", "error")
          alert('Mauvais login / mot de passe');
        },
      });
    }
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.form.get(fieldName);

    return !!(field && field.invalid && (field.dirty || field.touched));
  }
}
