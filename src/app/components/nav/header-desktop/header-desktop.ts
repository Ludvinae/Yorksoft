import { Component, inject } from '@angular/core';
import { NavDesktop } from '../nav-desktop/nav-desktop';
import { Router } from '@angular/router';
import { Auth } from '../../../services/auth';

@Component({
  selector: 'app-header-desktop',
  imports: [NavDesktop],
  templateUrl: './header-desktop.html',
  styleUrl: './header-desktop.css',
})
export class HeaderDesktop {
  private auth = inject(Auth);

  constructor(protected router: Router) {}

  logout(): void {
    this.auth.logout();

    this.router.navigate(['/']);
  }
}
