import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/business/auth-service';
import { CenteredPage } from '../../components/layout/centered-page/centered-page';

@Component({
  selector: 'app-forbidden',
  imports: [RouterLink, CenteredPage],
  templateUrl: './forbidden.html',
  styleUrl: './forbidden.css',
})
export class Forbidden implements OnInit {
  auth = inject(AuthService);

  route = signal<string>('/');

  ngOnInit(): void {
    if (this.auth.jwtInfo()) {
      this.route.set('/dashboard');
    }
  }
}
