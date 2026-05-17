import { Component, EventEmitter, Output, signal } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-title-bar',
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './title-bar.html',
  styleUrl: './title-bar.css',
})
export class TitleBar {
  @Output() paramsChange = new EventEmitter<string>();

  params = '';
  search = '';

  filters = {
    assigned: false,
    priority: false,
  };

  onInput(): void {
    this.paramsChange.emit(this.params);
  }

  setFilter(filter: 'all' | 'assigned' | 'priority'): void {
    this.filters.assigned = false;
    this.filters.priority = false;

    switch (filter) {
      case 'assigned':
        this.params = '?status=assigned';
        this.filters.assigned = true;
        this.onInput();
        break;

      case 'priority':
        this.params = '?priorityGreaterThan=65';
        this.filters.priority = true;
        this.onInput();
        break;

      case 'all':
        this.params = '';
        this.onInput();
        break;
    }
  }
}
