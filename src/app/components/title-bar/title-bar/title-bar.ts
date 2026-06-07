import { Component, inject, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TicketStoreService } from '../../../services/business/ticket-store-service';
import { AuthService } from '../../../services/business/auth-service';
import { UserPreferencesService } from '../../../services/business/user-preferences-service';
import { TicketFilterStateEnum } from '../../../models/enum/ticket-filter-state-enum';
import { TicketFilterState } from '../../../services/ui/ticket-filter-state';

@Component({
  selector: 'app-title-bar',
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './title-bar.html',
  styleUrl: './title-bar.css',
})
export class TitleBar implements OnInit {
  auth = inject(AuthService);
  preferences = inject(UserPreferencesService);
  filterState = inject(TicketFilterState);

  buttonState = this.filterState.filter;
  currentUserRole: string | undefined = undefined;

  search = '';
  private searchTimeout: any;

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

  setFilter(filter: TicketFilterStateEnum): void {
    this.store.resetFilters();

    this.filterState.setFilter(filter);

    this.store.loadTickets();
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

  protected readonly TicketFilterStateEnum = TicketFilterStateEnum;
}
