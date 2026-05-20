import { computed, Injectable, signal } from '@angular/core';
import { TicketService } from './api/ticket-service';
import { TicketSearchCriteria } from '../models/page/TicketSearchCriteria';

@Injectable({
  providedIn: 'root',
})
export class TicketStoreService {
  constructor(private ticketService: TicketService) {}

  // Request
  filters = signal<TicketSearchCriteria>({keyword: '',});
  page = signal(0);
  size = signal(15);
  sortField = signal('modificationDate');
  sortDirection = signal<'asc' | 'desc'>('desc');

  // Response
  tickets = signal<TicketListDto[]>([]);
  totalPages = signal(0);
  totalElements = signal(0);
  loading = signal(false);

  // COMPUTED
  sort = computed(() => `${this.sortField()},${this.sortDirection()}`);

  // ACTIONS
  updateFilters(partial: Partial<TicketSearchCriteria>): void {
    this.filters.update((current) => ({
      ...current,
      ...partial,
    }));

    this.page.set(0);

    this.loadTickets();
  }

  setPage(page: number): void {
    this.page.set(page);

    this.loadTickets();
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

  loadTickets(): void {
    this.loading.set(true);

    this.ticketService.getTickets(this.filters(), this.page(), this.size(), this.sort()).subscribe({
      next: (response) => {

        console.log('API RESPONSE', response);
        this.tickets.set(response.content);
        console.log('SIGNAL VALUE', this.tickets());

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
