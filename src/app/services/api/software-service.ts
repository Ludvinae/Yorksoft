import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { SoftwareDetail } from '../../pages/admin/software/software-detail/software-detail';

@Injectable({
  providedIn: 'root',
})
export class SoftwareService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/software';

  readonly softwareList = signal<Software[]>([]);
  readonly softwareDetail = signal<SoftwareDetailDto[]>([]);

  get(id: number) {
    return this.http.get<Software>(`${this.apiUrl}/${id}`);
  }

  getDetail(id: number) {
    return this.http.get<SoftwareDetailDto>(`${this.apiUrl}/${id}/detail`);
  }

  getAll() {
    return this.http.get<Software[]>(`${this.apiUrl}/list`).pipe(
      tap((softwareList) => {
        this.softwareList.set(softwareList);
      }),
    );
  }

  getAllDetail() {
    return this.http.get<SoftwareDetailDto[]>(`${this.apiUrl}/list/detail`).pipe(
      tap((softwareList) => {
        this.softwareDetail.set(softwareList);
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
