import { inject, Injectable, signal } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { tap } from 'rxjs';
import { Page } from '../../models/search/Page';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.serverUrl}/client`;

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

  getClients(filters: ClientSearchCriteria, page: number, size: number, sort: string) {
    let params = new HttpParams().set('page', page).set('size', size).set('sort', sort);

    Object.entries(filters).forEach(([key, value]) => {
      if (value !== null && value !== undefined && value !== '') {
        // tableau
        if (Array.isArray(value)) {
          value.forEach((v) => {
            params = params.append(key, v);
          });
        } else {
          params = params.set(key, value.toString());
        }
      }
    });

    return this.http.get<Page<ClientListDto>>(`${environment.serverUrl}/client/list`, { params });
  }

  create(client: Client) {
    return this.http.post(`${this.apiUrl}`, client).pipe(tap(() => this.getAll().subscribe()));
  }

  update(id: number, client: Client) {
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
