import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class LicenseService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.serverUrl}/license`;

  readonly licenseList = signal<License[]>([]);

  get(id: number) {
    return this.http.get<License>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<License[]>(`${this.apiUrl}/list`).pipe(
      tap((licenseList) => {
        this.licenseList.set(licenseList);
      }),
    );
  }

  create(license: License) {
    return this.http.post(`${this.apiUrl}`, license).pipe(tap(() => this.getAll().subscribe()));
  }

  update(license: License, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, license).pipe(
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
