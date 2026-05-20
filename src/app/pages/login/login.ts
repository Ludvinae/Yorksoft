import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Auth } from '../../services/auth';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth-service';

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
          this.router.navigate(['/dashboard']);
        },
        error: (err) => {
          // this.notification.open("Mauvais login / mot de passe", "error")
          alert('Mauvais login / mot de passe');
        },
      });
    }
  }
}
