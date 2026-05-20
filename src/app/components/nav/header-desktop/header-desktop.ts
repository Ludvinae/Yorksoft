import { Component, inject } from '@angular/core';
import { NavDesktop } from '../nav-desktop/nav-desktop';
import { Router, RouterLink } from '@angular/router';
import { Auth } from '../../../services/auth';
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-header-desktop',
  imports: [NavDesktop, RouterLink],
  templateUrl: './header-desktop.html',
  styleUrl: './header-desktop.css',
})
export class HeaderDesktop {
  private auth = inject(AuthService);

  constructor(protected router: Router) {}

  logout(): void {
    this.auth.logout();
  }
}
