import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-field-select',
  imports: [],
  templateUrl: './field-select.html',
  styleUrl: './field-select.css',
})
export class FieldSelect {

  @Input() labelKey: string = "défaut";
  @Input() valueKey: string = "choisir";
  @Input() options: SelectOption[] = [];
}
