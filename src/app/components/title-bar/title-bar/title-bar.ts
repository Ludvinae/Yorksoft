import { Component, inject, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TicketSearchCriteria } from '../../../models/page/TicketSearchCriteria';
import { TicketStoreService } from '../../../services/business/ticket-store-service';
import {AuthService} from '../../../services/business/auth-service';
import { UserPreferencesService } from '../../../services/business/user-preferences-service';

@Component({
  selector: 'app-title-bar',
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './title-bar.html',
  styleUrl: './title-bar.css',
})
export class TitleBar implements OnInit {
  auth = inject(AuthService);
  preferences = inject(UserPreferencesService)

  buttonState = 'all';

  search = '';
  private searchTimeout: any;

  filters: TicketSearchCriteria = {};
  showClosedTickets = true;

  constructor(public store: TicketStoreService) {}

  ngOnInit(): void {
    const preference = this.preferences.getPreferences();

    this.showClosedTickets = preference.showClosedTickets;

    if (!this.showClosedTickets) {
      this.store.updateFilters({
        statusesExcluded: ['CLOSED'],
      });
    }
  }

  setFilter(filter: 'all' | 'assigned' | 'priority'): void {
    this.filters = {};
    const userId = this.auth.jwtInfo() != null ? this.auth.jwtInfo()?.id : 0;

    switch (filter) {
      case 'assigned':
        // prevoir le cas ou pas d'id user
        this.store.updateFilters({
          assignedTo: this.auth.jwtInfo()?.id,
          priorityGreaterThan: undefined,
        });
        this.buttonState = 'assigned';
        break;

      case 'priority':
        this.store.updateFilters({ assignedTo: undefined, priorityGreaterThan: 65 });
        this.buttonState = 'priority';
        break;

      case 'all':
        this.store.updateFilters({ assignedTo: undefined, priorityGreaterThan: undefined });
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

  onClosedToggleChange(): void {
    this.preferences.updatePreferences({
      showClosedTickets: this.showClosedTickets,
    });

    if (this.showClosedTickets) {
      this.store.updateFilters({ statusesExcluded: [] });
    } else {
      this.store.updateFilters({ statusesExcluded: ['CLOSED'] });
    }
  }
}
