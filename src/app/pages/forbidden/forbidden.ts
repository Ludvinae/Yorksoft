import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-forbidden',
  imports: [RouterLink],
  templateUrl: './forbidden.html',
  styleUrl: './forbidden.css',
})
export class Forbidden implements OnInit {
  auth = inject(AuthService);

  route = signal<string>('/');

  ngOnInit(): void {
    if (this.auth.jwtInfo()) {
      this.route.set("/dashboard");
    }
  }
}
