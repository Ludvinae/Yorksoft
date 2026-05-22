import { Component, Input } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-field-textarea',
  imports: [ReactiveFormsModule],
  templateUrl: './field-textarea.html',
  styleUrl: './field-textarea.css',
})
export class FieldTextarea {
  @Input() labelKey: string = 'défaut';
  @Input() placeholder: string = 'Entrez votre texte ici';
  @Input() control!: FormControl;
  @Input() errorMessages: Record<string, string> = {};

  get errorMessage(): string | null {
    if (!this.control.errors || (!this.control.touched && !this.control.dirty)) {
      return null;
    }

    const firstKey = Object.keys(this.control.errors)[0];
    return this.errorMessages[firstKey] ?? 'Champ invalide';
  }
}
