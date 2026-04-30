import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/comment';

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

  create(comment: Comment) {
    return this.http.post(`${this.apiUrl}`, comment).pipe(tap(() => this.getAll().subscribe()));
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
