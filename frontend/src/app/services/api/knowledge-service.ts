import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class KnowledgeService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.serverUrl}/knowledge`;

  readonly knowledgeList = signal<Knowledge[]>([]);

  get(id: number) {
    return this.http.get<Knowledge>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<Knowledge[]>(`${this.apiUrl}/list`).pipe(
      tap((knowledgeList) => {
        this.knowledgeList.set(knowledgeList);
      }),
    );
  }

  create(knowledge: Knowledge) {
    return this.http.post<Knowledge>(`${this.apiUrl}`, knowledge).pipe(tap(() => this.getAll().subscribe()));
  }

  update(knowledge: Knowledge, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, knowledge).pipe(
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

  searchKnowledge(request: KnowledgeSearch): Observable<KnowledgeSelect[]> {
    return this.http.post<KnowledgeSelect[]>(
      `${this.apiUrl}/search`,
      request
    );
  }
}
