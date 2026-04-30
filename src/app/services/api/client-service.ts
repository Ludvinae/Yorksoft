import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/client';

  readonly clientList = signal<Client[]>([]);

  get(id: number) {
    return this.http.get<Client>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<Client[]>(`${this.apiUrl}/list`).pipe(
      tap((clientList) => {
        this.clientList.set(clientList);
      }),
    );
  }

  create(client: Client) {
    return this.http
      .post(`${this.apiUrl}`, client)
      .pipe(tap(() => this.getAll().subscribe()));
  }

  update(client: Client, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, client).pipe(
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
