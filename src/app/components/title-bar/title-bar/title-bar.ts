import { Component, EventEmitter, Output, signal } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TicketSearchCriteria } from '../../../models/page/TicketSearchCriteria';
import { filter } from 'rxjs';

@Component({
  selector: 'app-title-bar',
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './title-bar.html',
  styleUrl: './title-bar.css',
})
export class TitleBar {
  @Output() paramsChange = new EventEmitter<TicketSearchCriteria>();

  params = '';
  search = '';

  filters: TicketSearchCriteria = {};

  onInput(): void {
    this.paramsChange.emit(this.filters);
  }

  setFilter(filter: 'all' | 'assigned' | 'priority'): void {
    this.filters = {};

    switch (filter) {
      case 'assigned':
        this.params = '?status=assigned';
        this.filters.statuses = ['ASSIGNED'];
        this.onInput();
        break;

      case 'priority':
        this.params = '?priorityGreaterThan=65';
        this.filters.priorityGreaterThan = 65;
        this.onInput();
        break;

      case 'all':
        this.params = '';
        this.onInput();
        break;
    }
  }
}
