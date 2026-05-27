import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.serverUrl}/comment`;

  readonly commentList = signal<Comment[]>([]);

  get(id: number) {
    return this.http.get<Comment>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<Comment[]>(`${this.apiUrl}/list`).pipe(
      tap((commentList) => {
        this.commentList.set(commentList);
      }),
    );
  }

  create(comment: CommentDto) {
    return this.http.post(`${this.apiUrl}`, comment).pipe(tap(() => this.getAll().subscribe()));
  }

  createComment(comment: CommentDto): Observable<TicketDetailComment> {
    return this.http.post<TicketDetailComment>(`${this.apiUrl}/comment`, comment);
  }

  update(comment: Comment, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, comment).pipe(
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
