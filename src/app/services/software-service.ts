import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, tap } from 'rxjs';
import { toCreateDto, toForm } from '../mapper/software.mapper';

@Injectable({
  providedIn: 'root',
})
export class SoftwareService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/software';

  readonly softwareList = signal<SoftwareForm[]>([]);

  get(id: number) {
    return this.http
      .get<SoftwareResponseDto>(`${this.apiUrl}/${id}`)
      .pipe(map((dto) => toForm(dto)));
  }

  getAll() {
    return this.http.get<SoftwareResponseDto[]>(`${this.apiUrl}/list`).pipe(
      map((dtos) => dtos
        .map((dto) => toForm(dto))),
      tap((forms) => {
        this.softwareList.set(forms);
      }),
    );
  }

  create(form: SoftwareForm) {
    const dto = toCreateDto(form);

    return this.http.post(`${this.apiUrl}`, dto)
      .pipe(tap(() => this.getAll().subscribe()));
  }

  update(form: SoftwareForm, id: number) {
    const dto = toCreateDto(form);

    return this.http.put(`${this.apiUrl}/${id}`, dto)
      .pipe(tap(() => this.getAll().subscribe()));
  }

  delete(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`).pipe(
      tap((result) => {
        this.getAll().subscribe();
      }),
    );
  }
}
