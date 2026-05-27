import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AssignmentService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.serverUrl}/assignment`;

  readonly assignmentList = signal<Assignment[]>([]);

  get(id: number) {
    return this.http.get<Assignment>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<Assignment[]>(`${this.apiUrl}/list`).pipe(
      tap((assignmentList) => {
        this.assignmentList.set(assignmentList);
      }),
    );
  }

  create(assignment: Assignment) {
    return this.http.post(`${this.apiUrl}`, assignment).pipe(tap(() => this.getAll().subscribe()));
  }

  update(assignment: Assignment, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, assignment).pipe(
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
