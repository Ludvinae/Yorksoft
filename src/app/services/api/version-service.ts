import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class VersionService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/version';

  readonly versionList = signal<Version[]>([]);

  get(id: number) {
    return this.http.get<Version>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<Version[]>(`${this.apiUrl}/list`).pipe(
      tap((versionList) => {
        this.versionList.set(versionList);
      }),
    );
  }

  create(version: Version) {
    return this.http.post(`${this.apiUrl}`, version)
      .pipe(tap(() => this.getAll().subscribe()));
  }

  update(version: Version, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, version).pipe(
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
