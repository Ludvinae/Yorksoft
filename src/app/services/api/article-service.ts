import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.serverUrl}/article`;

  readonly articleList = signal<Article[]>([]);

  get(id: number) {
    return this.http.get<Article>(`${this.apiUrl}/${id}`);
  }

  getAll() {
    return this.http.get<Article[]>(`${this.apiUrl}/list`).pipe(
      tap((articleList) => {
        this.articleList.set(articleList);
      }),
    );
  }

  create(article: Article) {
    return this.http.post(`${this.apiUrl}`, article).pipe(tap(() => this.getAll().subscribe()));
  }

  update(article: Article, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, article).pipe(
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
