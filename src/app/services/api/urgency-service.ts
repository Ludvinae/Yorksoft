import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UrgencyService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.serverUrl}/urgency`;

  readonly urgencyList = signal<Urgency[]>([]);

  get(id: number) {
    return this.http.get<Urgency>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<Urgency[]>(`${this.apiUrl}/list`).pipe(
      tap((urgencyList) => {
        this.urgencyList.set(urgencyList);
      }),
    );
  }

  create(urgency: Urgency) {
    return this.http.post(`${this.apiUrl}`, urgency).pipe(tap(() => this.getAll().subscribe()));
  }

  update(urgency: Urgency, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, urgency).pipe(
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
