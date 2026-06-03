import { Component, inject, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TicketSearchCriteria } from '../../../models/search/TicketSearchCriteria';
import { TicketStoreService } from '../../../services/business/ticket-store-service';
import { AuthService } from '../../../services/business/auth-service';
import { UserPreferencesService } from '../../../services/business/user-preferences-service';

@Component({
  selector: 'app-title-bar',
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './title-bar.html',
  styleUrl: './title-bar.css',
})
export class TitleBar implements OnInit {
  auth = inject(AuthService);
  preferences = inject(UserPreferencesService);

  buttonState = 'none';
  currentUserRole: string | undefined = undefined;

  search = '';
  private searchTimeout: any;

  filters: TicketSearchCriteria = {};
  showClosedTickets = true;

  constructor(public store: TicketStoreService) {}

  ngOnInit(): void {
    const preference = this.preferences.getPreferences();
    this.currentUserRole = this.auth.jwtInfo()?.role;

    this.showClosedTickets = preference.showClosedTickets;
    this.store.setShowClosedTickets(this.showClosedTickets);

    if (!this.showClosedTickets) {
      this.store.updateFilters({
        statusesExcluded: ['CLOSED'],
      });
    }
  }

  setFilter(filter: 'all' | 'assigned' | 'priority' | 'open'): void {
    this.filters = {};

    switch (filter) {
      case 'open':
        this.store.setFilters({
          statuses: ['OPEN'],
        });
        this.buttonState = 'open';
        break;

      case 'assigned':
        // prevoir le cas ou pas d'id user
        this.store.setFilters({
          assignedTo: this.auth.jwtInfo()?.id,
        });
        this.buttonState = 'assigned';
        break;

      case 'priority':
        this.store.setFilters({
          priorityGreaterThan: 65,
        });
        this.buttonState = 'priority';
        break;

      case 'all':
        this.store.setFilters({});
        this.buttonState = 'all';
        break;

      default:
        this.store.resetFilters();
        this.buttonState = 'none';
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

    this.store.setShowClosedTickets(this.showClosedTickets);
  }
}
