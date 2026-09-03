import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class HistoryService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.serverUrl}/history`;

  readonly historyList = signal<History[]>([]);

  get(id: number) {
    return this.http.get<History>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<History[]>(`${this.apiUrl}/list`).pipe(
      tap((historyList) => {
        this.historyList.set(historyList);
      }),
    );
  }

  create(history: History) {
    return this.http.post(`${this.apiUrl}`, history).pipe(tap(() => this.getAll().subscribe()));
  }

  update(history: History, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, history).pipe(
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
