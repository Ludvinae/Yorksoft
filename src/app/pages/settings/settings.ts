import { Component, inject } from '@angular/core';
import { AuthService } from '../../services/business/auth-service';

@Component({
  selector: 'app-settings',
  imports: [],
  templateUrl: './settings.html',
  styleUrl: './settings.css',
})
export class Settings {
  auth = inject(AuthService);

  setTheme(theme: string) {
    const userId = this.auth.jwtInfo()?.id;
    document.documentElement.setAttribute('data-theme', theme);
    localStorage.setItem(`theme_${userId}`, theme);
  }
}
