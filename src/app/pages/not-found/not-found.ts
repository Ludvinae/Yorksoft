import { Component, inject, OnInit, signal } from '@angular/core';
import {RouterLink} from "@angular/router";
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-not-found',
  imports: [RouterLink],
  templateUrl: './not-found.html',
  styleUrl: './not-found.css',
})
export class NotFound implements OnInit {
  auth = inject(AuthService);

  route = signal<string>('/');

  ngOnInit(): void {
    if (this.auth.jwtInfo()) {
      this.route.set('/dashboard');
    }
  }
}
