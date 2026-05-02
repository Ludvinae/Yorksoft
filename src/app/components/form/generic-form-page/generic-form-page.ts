import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-generic-form-page',
  imports: [FormsModule, RouterLink, ReactiveFormsModule],
  templateUrl: './generic-form-page.html',
  styleUrl: './generic-form-page.css',
})
export class GenericFormPage {
  @Input() title?: string;
  @Input() loading = false;
  @Input() error?: string;
  @Input() submitLabel = 'Valider';
  @Input() formGroup!: FormGroup;
  @Input() routeId!: string;
  @Input() isEditMode!: boolean;

  @Output() submitForm = new EventEmitter<void>();

  onSubmit() {
    this.submitForm.emit();
  }
}
