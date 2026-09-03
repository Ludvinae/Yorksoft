import { Component } from '@angular/core';
import { ClientStoreService } from '../../../services/business/client-store-service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-title-client',
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './title-client.html',
  styleUrl: './title-client.css',
})
export class TitleClient {
  buttonState = 'all';

  search = '';
  private searchTimeout: any;

  filters: ClientSearchCriteria = {};

  constructor(public store: ClientStoreService) {}

  setFilter(filter: 'all' | 'asking' | 'important'): void {
    this.filters = {};

    switch (filter) {
      case 'asking':
        this.store.resetFilters();
        this.store.updateFilters({ hasOpenTicket: true });
        this.buttonState = 'asking';
        break;

      case 'important':
        this.store.resetFilters();
        this.store.updateFilters({ importanceGreaterThan: 1 });
        this.buttonState = 'important';
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
      this.store.updateFilters({ keyword: value });
    }, 300);
  }
}
