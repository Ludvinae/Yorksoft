import { Component, inject } from '@angular/core';
import { NavDesktop } from '../nav-desktop/nav-desktop';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../services/business/auth-service';
import { TitleCasePipe } from '@angular/common';
import { Avatar } from '../../avatar/avatar';

@Component({
  selector: 'app-header-desktop',
  imports: [NavDesktop, RouterLink, TitleCasePipe, Avatar],
  templateUrl: './header-desktop.html',
  styleUrl: './header-desktop.css',
})
export class HeaderDesktop {
  protected auth = inject(AuthService);

  constructor(protected router: Router) {}

  logout(): void {
    this.auth.logout();
  }
}
