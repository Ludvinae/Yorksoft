import { Component, Input } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-field-text',
  imports: [ReactiveFormsModule],
  templateUrl: './field-text.html',
  styleUrl: './field-text.css',
})
export class FieldText {
  @Input() labelKey: string = 'défaut';
  @Input() placeholder: string = 'Entrez votre texte ici';
  @Input() type: string = 'text';
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
