import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class DirectorService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.serverUrl}/director`;

  readonly directorList = signal<Director[]>([]);

  get(id: number) {
    return this.http.get<Director>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<Director[]>(`${this.apiUrl}/list`).pipe(
      tap((directorList) => {
        this.directorList.set(directorList);
      }),
    );
  }

  create(director: DirectorCreateDto) {
    return this.http.post(`${this.apiUrl}`, director).pipe(tap(() => this.getAll().subscribe()));
  }

  update(director: Director, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, director).pipe(
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
