import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AppUserService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.serverUrl}/user`;

  readonly appUserList = signal<AppUser[]>([]);

  get(id: number) {
    return this.http.get<AppUser>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<AppUser[]>(`${this.apiUrl}/list`).pipe(
      tap((appUserList) => {
        this.appUserList.set(appUserList);
      }),
    );
  }

  create(appUser: AppUser) {
    return this.http.post(`${this.apiUrl}`, appUser).pipe(tap(() => this.getAll().subscribe()));
  }

  update(appUser: AppUser, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, appUser).pipe(
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
