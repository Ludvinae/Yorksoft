import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SoftwareTypeService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/software-type';

  readonly softwareTypeList = signal<SoftwareType[]>([]);

  get(id: number) {
    return this.http.get<SoftwareType>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<SoftwareType[]>(`${this.apiUrl}/list`).pipe(
      tap((softwareTypeList) => {
        this.softwareTypeList.set(softwareTypeList);
      }),
    );
  }

  create(softwareType: SoftwareType) {
    return this.http
      .post(`${this.apiUrl}`, softwareType)
      .pipe(tap(() => this.getAll().subscribe()));
  }

  update(softwareType: SoftwareType, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, softwareType).pipe(
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
