import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Auth } from '../../services/auth';

@Component({
  selector: 'app-login',
  imports: [],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  private auth = inject(Auth);

  constructor(protected router: Router) {}

  login(): void {
    this.auth.login();

    this.router.navigate(['/dashboard']);
  }
}
