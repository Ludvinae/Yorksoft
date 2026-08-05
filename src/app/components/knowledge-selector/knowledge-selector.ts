import { Component, DestroyRef, EventEmitter, inject, Input, OnInit, Output } from '@angular/core';
import { KnowledgeService } from '../../services/api/knowledge-service';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-knowledge-selector',
  imports: [ReactiveFormsModule],
  templateUrl: './knowledge-selector.html',
  styleUrl: './knowledge-selector.css',
})
export class KnowledgeSelector implements OnInit {
  private readonly knowledgeService = inject(KnowledgeService);
  private readonly destroyRef = inject(DestroyRef);

  @Input({ required: true }) idTheme!: number;
  @Input({ required: true }) idVersion!: number;
  @Input() selectedKnowledge?: KnowledgeSelect | null;

  @Output() knowledgeSelected = new EventEmitter<KnowledgeSelect>();
  @Output() createKnowledge = new EventEmitter<void>();

  protected searchControl = new FormControl('');
  protected results: KnowledgeSelect[] = [];

  ngOnInit(): void {
    this.searchControl.valueChanges
      .pipe(
        debounceTime(300),
        distinctUntilChanged(),
        switchMap((search) =>
          this.knowledgeService.searchKnowledge({
            search: search ?? '',
            idTheme: this.idTheme,
            idVersion: this.idVersion,
          }),
        ),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe((results) => {
        this.results = results;
      });

    this.searchControl.setValue('');
  }

  protected selectKnowledge(knowledge: KnowledgeSelect): void {
    this.selectedKnowledge = knowledge;

    this.searchControl.setValue(knowledge.subject, { emitEvent: false });

    this.results = [];

    this.knowledgeSelected.emit(knowledge);
  }

  protected openCreateModal(): void {
    this.createKnowledge.emit();
  }
}
