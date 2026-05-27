import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class StatusService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.serverUrl}/status`;

  readonly statusList = signal<Status[]>([]);

  get(id: number) {
    return this.http.get<Status>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<Status[]>(`${this.apiUrl}/list`).pipe(
      tap((statusList) => {
        this.statusList.set(statusList);
      }),
    );
  }

  create(status: Status) {
    return this.http.post(`${this.apiUrl}`, status).pipe(tap(() => this.getAll().subscribe()));
  }

  update(status: Status, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, status).pipe(
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
