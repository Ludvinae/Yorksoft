import { Component, Input } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-field-select',
  imports: [ReactiveFormsModule],
  templateUrl: './field-select.html',
  styleUrl: './field-select.css',
})
export class FieldSelect {
  @Input() labelKey!: string;
  @Input() control!: FormControl;
  @Input() options: any[] = [];

  @Input() valueField: string = 'id';
  @Input() labelField: string = 'label';

  @Input() placeholder: string = 'Choisir...';
  @Input() errorMessages: Record<string, string> = {};

  ngOnChanges() {
    if (!this.control) return;

    if (this.options.length === 0) {
      this.control.disable({ emitEvent: false });
    } else {
      this.control.enable({ emitEvent: false });
    }
  }

  get errorMessage(): string | null {
    if (!this.control.errors || !this.control.touched) return null;

    const firstKey = Object.keys(this.control.errors)[0];
    return this.errorMessages[firstKey] ?? 'Champ invalide';
  }

  getValue(option: any) {
    return option[this.valueField];
  }

  getLabel(option: any) {
    return option[this.labelField];
  }
}
