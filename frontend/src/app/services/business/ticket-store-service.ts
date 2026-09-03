import { computed, effect, inject, Injectable, signal } from '@angular/core';
import { TicketService } from '../api/ticket-service';
import { TicketSearchCriteria } from '../../models/search/TicketSearchCriteria';
import { TicketFilterState } from '../ui/ticket-filter-state';

@Injectable({
  providedIn: 'root',
})
export class TicketStoreService {
  constructor(private ticketService: TicketService) {}
  private filterState = inject(TicketFilterState);

  // Filtres métier
  filters = signal<TicketSearchCriteria>({});

  // Préférences d'affichage
  showClosedTickets = signal(true);

  page = signal(0);
  size = signal(10);
  sortField = signal('modificationDate');
  sortDirection = signal<'asc' | 'desc'>('desc');

  tickets = signal<TicketListDto[]>([]);
  totalPages = signal(0);
  totalElements = signal(0);
  loading = signal(false);

  sort = computed(() => `${this.sortField()},${this.sortDirection()}`);

  // Filtres réellement envoyés à l'API
  effectiveFilters = computed(() => {
    const filters = {
      ...this.filters(),
      ...this.filterState.criteria(),
    };

    if (filters.closedAfter || filters.closedBefore) {
      return filters;
    }

    if (!this.showClosedTickets()) {
      filters.statusesExcluded = ['CLOSED'];
    }

    return filters;
  });

  updateFilters(partial: Partial<TicketSearchCriteria>): void {
    this.filters.update((current) => ({
      ...current,
      ...partial,
    }));

    this.page.set(0);
    this.loadTickets();
  }

  resetFilters(): void {
    this.filters.set({});
  }

  setFilters(filters: TicketSearchCriteria): void {
    console.log('SET FILTERS', filters);
    this.filters.set(filters);

    this.page.set(0);
    this.loadTickets();
  }

  setPage(page: number): void {
    this.page.set(page);

    this.loadTickets();
  }

  isFirstPage(): boolean {
    return this.page() <= 0;
  }

  isLastPage(): boolean {
    return this.page() >= this.totalPages() - 1;
  }

  previousPage(): void {
    if (!this.isFirstPage()) {
      this.setPage(this.page() - 1);
    }
  }

  nextPage(): void {
    if (!this.isLastPage()) {
      this.setPage(this.page() + 1);
    }
  }

  setSort(field: string): void {
    if (this.sortField() === field) {
      this.sortDirection.update((dir) => (dir === 'asc' ? 'desc' : 'asc'));
    } else {
      this.sortField.set(field);

      this.sortDirection.set('asc');
    }

    this.loadTickets();
  }

  setShowClosedTickets(show: boolean): void {
    this.showClosedTickets.set(show);

    this.page.set(0);
    this.loadTickets();
  }

  loadTickets(): void {
    this.loading.set(true);
    console.log('STORE FILTERS', this.filters());

    console.log('SELECTED FILTER', this.filterState.selectedFilter());

    console.log('CRITERIA', this.filterState.criteria());

    this.ticketService
      .getTickets(
        this.effectiveFilters(), // <- important
        this.page(),
        this.size(),
        this.sort(),
      )
      .subscribe({
        next: (response) => {
          this.tickets.set(response.content);
          this.totalPages.set(response.totalPages);
          this.totalElements.set(response.totalElements);
          this.loading.set(false);
        },
        error: (err) => {
          console.error(err);
          this.loading.set(false);
        },
      });
  }
}
