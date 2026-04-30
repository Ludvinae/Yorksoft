import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ImpactService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/impact';

  readonly impactList = signal<Impact[]>([]);

  get(id: number) {
    return this.http.get<Impact>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<Impact[]>(`${this.apiUrl}/list`).pipe(
      tap((impactList) => {
        this.impactList.set(impactList);
      }),
    );
  }

  create(impact: Impact) {
    return this.http
      .post(`${this.apiUrl}`, impact)
      .pipe(tap(() => this.getAll().subscribe()));
  }

  update(impact: Impact, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, impact).pipe(
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
