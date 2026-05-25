import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TechnicianService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/technician';

  readonly technicianList = signal<Technician[]>([]);
  readonly technicianWorkload = signal<TechnicianWorkloadDetailedDto[]>([]);

  get(id: number) {
    return this.http.get<Technician>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<Technician[]>(`${this.apiUrl}/list`).pipe(
      tap((technicianList) => {
        this.technicianList.set(technicianList);
      }),
    );
  }

  getAllWorkload() {
    return this.http.get<TechnicianWorkloadDetailedDto[]>(`${this.apiUrl}/list/workload`).pipe(
      tap((technicianList) => {
        this.technicianWorkload.set(technicianList);
      }),
    );
  }

  create(technician: Technician) {
    return this.http.post(`${this.apiUrl}`, technician).pipe(tap(() => this.getAll().subscribe()));
  }

  update(technician: Technician, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, technician).pipe(
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
