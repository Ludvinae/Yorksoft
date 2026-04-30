import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/theme';

  readonly themeList = signal<Theme[]>([]);

  get(id: number) {
    return this.http.get<Theme>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<Theme[]>(`${this.apiUrl}/list`).pipe(
      tap((themeList) => {
        this.themeList.set(themeList);
      }),
    );
  }

  create(theme: Theme) {
    return this.http
      .post(`${this.apiUrl}`, theme)
      .pipe(tap(() => this.getAll().subscribe()));
  }

  update(theme: Theme, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, theme).pipe(
      tap((result) => {
        this.getAll().subscribe();
      }),
    );
  }

  delete(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`).pipe(
      tap((result) => {
        this.getAll().subscribe();
      }),
    );
  }
}
