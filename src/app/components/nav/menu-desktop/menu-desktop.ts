import { Component, inject } from '@angular/core';
import { NavDesktop } from '../nav-desktop/nav-desktop';
import { Router } from '@angular/router';
import { Auth } from '../../../services/auth';

@Component({
  selector: 'app-menu-desktop',
  imports: [NavDesktop],
  templateUrl: './menu-desktop.html',
  styleUrl: './menu-desktop.css',
})
export class MenuDesktop {
  private auth = inject(Auth);

  constructor(protected router: Router) {}

  logout(): void {
    this.auth.logout();

    this.router.navigate(['/']);
  }
}
