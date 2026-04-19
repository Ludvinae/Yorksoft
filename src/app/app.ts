import { Component, inject, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Auth } from './services/auth';
import { BreakpointSwitch } from './services/breakpoint-switch';
import { FormsModule } from '@angular/forms';
import { HeaderDesktop } from './components/nav/header-desktop/header-desktop';
import { HeaderMobile } from './components/nav/header-mobile/header-mobile';
import { NavMobile } from './components/nav/nav-mobile/nav-mobile';

/**
 * Composant principal, gère le layout et injecte le header et le contenu des pages
 * S'adapte au format (via isMobile) et à l'authentification (isAuthenticated)
 */
@Component({
  selector: 'app-root',
  imports: [RouterOutlet, FormsModule, HeaderDesktop, HeaderMobile, NavMobile],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  private auth = inject(Auth);
  private breakpoint = inject(BreakpointSwitch);

  protected readonly title = signal('Suivi-MNS-Frontend');

  isAuthenticated = this.auth.isAuthenticated;

  isMobile = this.breakpoint.isMobile;

  isDrawerOpen = false;

  toggleDrawer() {
    this.isDrawerOpen = !this.isDrawerOpen;
  }

  closeDrawer() {
    this.isDrawerOpen = false;
  }
}
