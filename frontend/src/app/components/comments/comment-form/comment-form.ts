import { Component, EventEmitter, inject, input, OnInit, output, Output } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { submit } from '@angular/forms/signals';

@Component({
  selector: 'app-comment-form',
  imports: [ReactiveFormsModule],
  templateUrl: './comment-form.html',
  styleUrl: './comment-form.css',
})
export class CommentForm implements OnInit {
  private readonly fb = inject(FormBuilder);

  submitComment = output<string>();
  readonly cancel = output<void>();

  readonly initialValue = input('');
  readonly submitLabel = input('Envoyer');

  protected readonly form = this.fb.nonNullable.group({
    content: ['', [Validators.required, Validators.maxLength(2000)]],
  });

  ngOnInit(): void {
    this.form.patchValue({
      content: this.initialValue(),
    });
  }

  protected submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const content = this.form.controls.content.value.trim();

    if (!content) {
      return;
    }

    this.submitComment.emit(content);

    this.form.reset();
  }
}
