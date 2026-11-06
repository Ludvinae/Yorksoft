import { Component, computed, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { TitleBar } from '../../components/title-bar/title-bar/title-bar';
import { KnowledgeService } from '../../services/api/knowledge-service';
import { ThemeService } from '../../services/api/theme-service';
import { AuthService } from '../../services/business/auth-service';

@Component({
  selector: 'app-knowledge',
  imports: [TitleBar, FormsModule, ReactiveFormsModule, DatePipe],
  templateUrl: './knowledge.html',
  styleUrl: './knowledge.css',
})
export class Knowledge implements OnInit, OnDestroy {
  private readonly knowledgeService = inject(KnowledgeService);
  private readonly themeService = inject(ThemeService);
  private readonly authService = inject(AuthService);
  private readonly fb = inject(FormBuilder);

  readonly themes = this.themeService.themeList;
  readonly articles = signal<KnowledgeArticle[]>([]);
  readonly loading = signal(true);
  readonly loadError = signal<string | null>(null);

  readonly search = signal('');
  readonly idTheme = signal<number | null>(null);

  // Article whose resolution/procedures are shown, and article being edited
  readonly expandedId = signal<number | null>(null);
  readonly editingId = signal<number | null>(null);
  readonly saving = signal(false);
  readonly editError = signal<string | null>(null);

  // Directors can read the knowledge base, but only technicians, managers and admins can edit it (backend @IsTechnician)
  readonly canEdit = computed(() => {
    const role = this.authService.jwtInfo()?.role;
    return role === 'TECHNICIAN' || role === 'MANAGER' || role === 'ADMIN';
  });

  // Trial accounts can only edit the articles they wrote (backend returns 403 otherwise)
  canEditArticle(article: KnowledgeArticle): boolean {
    if (!this.canEdit()) return false;
    if (!this.authService.isTrialAccount()) return true;
    return article.idAuthor === this.authService.jwtInfo()?.id;
  }

  readonly editForm = this.fb.nonNullable.group({
    subject: ['', [Validators.required, Validators.maxLength(255)]],
    description: [''],
    resolution: [''],
    idTheme: [0, Validators.required],
  });

  private loadSub?: Subscription;

  ngOnInit(): void {
    this.themeService.getAll().subscribe();
    this.load();
  }

  ngOnDestroy(): void {
    this.loadSub?.unsubscribe();
  }

  onSearch(value: string): void {
    this.search.set(value);
    this.load();
  }

  onThemeChange(value: number | null): void {
    this.idTheme.set(value);
    this.load();
  }

  toggleExpanded(id: number): void {
    this.expandedId.update((current) => (current === id ? null : id));
  }

  startEdit(article: KnowledgeArticle): void {
    this.editError.set(null);
    this.editForm.reset({
      subject: article.subject,
      description: article.description ?? '',
      resolution: article.resolution ?? '',
      idTheme: article.idTheme,
    });
    this.editingId.set(article.idKnowledge);
    this.expandedId.set(article.idKnowledge);
  }

  cancelEdit(): void {
    this.editingId.set(null);
    this.editError.set(null);
  }

  saveEdit(id: number): void {
    if (this.editForm.invalid) {
      this.editForm.markAllAsTouched();
      return;
    }

    const value = this.editForm.getRawValue();
    this.saving.set(true);
    this.editError.set(null);

    this.knowledgeService
      .updateArticle(id, {
        subject: value.subject.trim(),
        description: value.description,
        resolution: value.resolution,
        idTheme: Number(value.idTheme),
        versionIds: null,
      })
      .subscribe({
        next: () => {
          this.saving.set(false);
          this.editingId.set(null);
          this.load();
        },
        error: () => {
          this.saving.set(false);
          this.editError.set("L'article n'a pas pu être enregistré.");
        },
      });
  }

  private load(): void {
    // Drop any in-flight request so a slow older response can't overwrite a newer one
    this.loadSub?.unsubscribe();
    this.loading.set(true);
    this.loadError.set(null);

    this.loadSub = this.knowledgeService.getArticles(this.search(), this.idTheme()).subscribe({
      next: (articles) => {
        this.articles.set(articles);
        this.loading.set(false);
      },
      error: () => {
        this.articles.set([]);
        this.loading.set(false);
        this.loadError.set('Impossible de charger la base de connaissances.');
      },
    });
  }
}
