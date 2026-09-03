import { Injectable } from '@angular/core';
import { AuthService } from './auth-service';
import { UserPreferences } from '../UserPreference';

@Injectable({
  providedIn: 'root',
})
export class UserPreferencesService {
  constructor(private auth: AuthService) {}

  private getStorageKey(): string {
    const userId = this.auth.jwtInfo()?.id;

    return `preferences_user_${userId}`;
  }

  getPreferences(): UserPreferences {
    const key = this.getStorageKey();

    const data = localStorage.getItem(key);

    if (!data) {
      return {
        showClosedTickets: false,
      };
    }

    return JSON.parse(data);
  }

  savePreferences(preferences: UserPreferences): void {
    localStorage.setItem(this.getStorageKey(), JSON.stringify(preferences));
  }

  updatePreferences(partial: Partial<UserPreferences>): void {
    const current = this.getPreferences();

    const updated = {
      ...current,
      ...partial,
    };

    this.savePreferences(updated);
  }
}
