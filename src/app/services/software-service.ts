import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SoftwareService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/';

  getSoftware(): Observable<Software[]> {
    return this.http.get<Software[]>(`${this.apiUrl}/software/list`);
  }
}
