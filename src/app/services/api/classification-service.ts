import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ClassificationService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/classification';

  readonly classificationList = signal<Classification[]>([]);

  get(id: number) {
    return this.http.get<Classification>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<Classification[]>(`${this.apiUrl}/list`).pipe(
      tap((classificationList) => {
        this.classificationList.set(classificationList);
      }),
    );
  }

  /*
  create(classification: Classification) {
    return this.http.post(`${this.apiUrl}`, classification).pipe(tap(() => this.getAll().subscribe()));
  }

  update(classification: Classification, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, classification ).pipe(
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

   */
}
