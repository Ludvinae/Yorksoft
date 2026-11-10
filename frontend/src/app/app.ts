import { Component, computed, inject, signal } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { BreakpointSwitch } from './services/business/breakpoint-switch';
import { FormsModule } from '@angular/forms';
import { HeaderDesktop } from './components/nav/header-desktop/header-desktop';
import { HeaderMobile } from './components/nav/header-mobile/header-mobile';
import { NavMobile } from './components/nav/nav-mobile/nav-mobile';
import { filter } from 'rxjs';
import { AuthService } from './services/business/auth-service';

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
  private breakpoint = inject(BreakpointSwitch);
  private router = inject(Router);
  private auth = inject(AuthService);

  protected readonly title = signal('Suivi-MNS-Frontend');
  protected currentUrl = signal(this.router.url);

  isMobile = this.breakpoint.isMobile;

  isDrawerOpen = false;

  constructor() {
    this.router.events.pipe(filter((event) => event instanceof NavigationEnd)).subscribe(() => {
      this.currentUrl.set(this.router.url);
    });
  }

  // No menu on the login page, nor for logged-out visitors on public pages (legal notice)
  displayNav = computed(() => {
    return this.currentUrl() !== '/' && this.auth.jwtInfo() !== null;
  });

  toggleDrawer() {
    this.isDrawerOpen = !this.isDrawerOpen;
  }

  closeDrawer() {
    this.isDrawerOpen = false;
  }
}
