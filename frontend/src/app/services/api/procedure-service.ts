import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ProcedureService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.serverUrl}/procedure`;

  readonly procedureList = signal<Procedure[]>([]);

  get(id: number) {
    return this.http.get<Procedure>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<Procedure[]>(`${this.apiUrl}/list`).pipe(
      tap((procedureList) => {
        this.procedureList.set(procedureList);
      }),
    );
  }

  create(procedure: Procedure) {
    return this.http.post(`${this.apiUrl}`, procedure).pipe(tap(() => this.getAll().subscribe()));
  }

  update(procedure: Procedure, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, procedure).pipe(
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
