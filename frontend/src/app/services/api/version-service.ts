import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class VersionService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.serverUrl}/version`;

  readonly versionList = signal<Version[]>([]);
  readonly versionDetail = signal<VersionDetailDto[]>([]);
  readonly versionSelect = signal<VersionSelectDto[]>([]);

  get(id: number) {
    return this.http.get<Version>(`${this.apiUrl}/${id}`);
  }

  getDetail(id: number) {
    return this.http.get<VersionDetailDto>(`${this.apiUrl}/${id}/detail`);
  }

  getAll() {
    return this.http.get<Version[]>(`${this.apiUrl}/list`).pipe(
      tap((versionList) => {
        this.versionList.set(versionList);
      }),
    );
  }

  getAllDto() {
    return this.http.get<VersionDetailDto[]>(`${this.apiUrl}/list/detail`).pipe(
      tap((versionList) => {
        this.versionDetail.set(versionList);
      }),
    );
  }

  getAllBySoftware(id: number) {
    return this.http.get<VersionSelectDto[]>(`${this.apiUrl}/list/${id}`).pipe(
      tap((versionList) => {
        this.versionSelect.set(versionList);
      }),
    );
  }

  create(version: Version) {
    return this.http.post(`${this.apiUrl}`, version).pipe(tap(() => this.getAll().subscribe()));
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
