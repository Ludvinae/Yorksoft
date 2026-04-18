import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
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
