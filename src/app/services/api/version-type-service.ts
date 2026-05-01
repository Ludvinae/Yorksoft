import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class VersionTypeService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/version-type';

  readonly versionTypeList = signal<VersionType[]>([]);

  get(id: number) {
    return this.http.get<VersionType>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<VersionType[]>(`${this.apiUrl}/list`).pipe(
      tap((versionTypeList) => {
        this.versionTypeList.set(versionTypeList);
      }),
    );
  }

  create(versionType: VersionType) {
    return this.http.post(`${this.apiUrl}`, versionType).pipe(tap(() => this.getAll().subscribe()));
  }

  update(versionType: VersionType, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, versionType).pipe(
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
