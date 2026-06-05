import { Component, EventEmitter, inject, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { KnowledgeService } from '../../services/api/knowledge-service';

@Component({
  selector: 'app-knowledge-create-modal',
  imports: [ReactiveFormsModule],
  templateUrl: './knowledge-create-modal.html',
  styleUrl: './knowledge-create-modal.css',
})
export class KnowledgeCreateModal implements OnInit {
  @Input({ required: true })
  idTheme!: number;

  @Input({ required: true })
  themeName!: string;

  @Input({ required: true })
  idVersion!: number;

  @Input({ required: true })
  versionLabel!: string;

  @Input()
  ticketTitle = '';

  @Input()
  ticketDescription = '';

  @Output()
  close = new EventEmitter<void>();

  @Output()
  created = new EventEmitter<Knowledge>();

  private readonly fb = inject(FormBuilder);

  private readonly knowledgeService = inject(KnowledgeService);

  protected readonly form = this.fb.nonNullable.group({
    subject: ['', Validators.required],
    description: ['', Validators.required],
    resolution: [''],
  });

  ngOnInit(): void {
    this.form.patchValue({
      subject: this.ticketTitle,
      description: this.ticketDescription,
    });
  }

  protected save(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.knowledgeService
      .create({
        idKnowledge: 0,

        subject: this.form.controls.subject.value,

        description: this.form.controls.description.value,

        resolution: this.form.controls.resolution.value,

        idTheme: this.idTheme,

        versionIds: [this.idVersion],

        idProcedure: 0,
      })
      .subscribe({
        next: (knowledge) => {
          this.created.emit(knowledge);

          this.close.emit();
        },
      });
  }

  protected cancel(): void {
    this.close.emit();
  }
}
