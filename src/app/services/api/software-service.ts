import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SoftwareService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/software';

  readonly softwareList = signal<Software[]>([]);

  get(id: number) {
    return this.http.get<Software>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<Software[]>(`${this.apiUrl}/list`).pipe(
      tap((softwareList) => {
        this.softwareList.set(softwareList);
      }),
    );
  }

  create(software: Software) {
    return this.http.post(`${this.apiUrl}`, software).pipe(tap(() => this.getAll().subscribe()));
  }

  update(software: Software, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, software).pipe(
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
