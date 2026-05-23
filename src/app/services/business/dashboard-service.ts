import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth-service';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  private http = inject(HttpClient);
  private auth = inject(AuthService);

  private apiUrl = 'http://localhost:8080/dashboard';

  readonly dashboard = signal<DashboardDto | null>(null);
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);

  loadDashboard(timeframe: number | null): void {

    this.loading.set(true);
    this.error.set(null);

    this.http.get<DashboardDto>(`${this.apiUrl}/${timeframe}`).subscribe({
      next: (data) => {
        this.dashboard.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Erreur dashboard');
        this.loading.set(false);
      },
    });
  }
}
