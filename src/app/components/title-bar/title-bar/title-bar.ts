import { Component, inject } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TicketSearchCriteria } from '../../../models/page/TicketSearchCriteria';
import { TicketStoreService } from '../../../services/ticket-store-service';
import {AuthService} from '../../../services/auth-service';

@Component({
  selector: 'app-title-bar',
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './title-bar.html',
  styleUrl: './title-bar.css',
})
export class TitleBar {
  auth = inject(AuthService);

  buttonState = 'all';

  search = "";
  private searchTimeout : any;

  filters: TicketSearchCriteria = {};

  constructor(public store: TicketStoreService) {}

  setFilter(filter: 'all' | 'assigned' | 'priority'): void {
    this.filters = {};
    const userId = this.auth.jwtInfo() != null ? this.auth.jwtInfo()?.id : 0


    switch (filter) {
      case 'assigned':
        this.store.resetFilters();
        // prevoir le cas ou pas d'id user
        this.store.updateFilters({ assignedTo:  this.auth.jwtInfo()?.id  });
        this.buttonState = 'assigned';
        break;

      case 'priority':
        this.store.resetFilters();
        this.store.updateFilters({ priorityGreaterThan: 65 });
        this.buttonState = 'priority';
        break;

      case 'all':
        this.store.resetFilters();
        this.store.updateFilters({});
        this.buttonState = 'all';
        break;
    }
  }

  // Fonction de recherche sur le titre, avec debounce 300ms
  onSearch(value: string): void {

    this.search = value;
    clearTimeout(this.searchTimeout);
    this.searchTimeout = setTimeout(() => {
      this.store.updateFilters({keyword: value})
    }, 300);
  }
}
