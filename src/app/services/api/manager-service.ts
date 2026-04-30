import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ManagerService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/manager';

  readonly managerList = signal<Manager[]>([]);

  get(id: number) {
    return this.http.get<Manager>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<Manager[]>(`${this.apiUrl}/list`).pipe(
      tap((managerList) => {
        this.managerList.set(managerList);
      }),
    );
  }

  create(manager: Manager) {
    return this.http
      .post(`${this.apiUrl}`, manager)
      .pipe(tap(() => this.getAll().subscribe()));
  }

  update(manager: Manager, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, manager).pipe(
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
