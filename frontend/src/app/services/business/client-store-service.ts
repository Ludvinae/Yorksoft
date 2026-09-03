import { computed, Injectable, signal } from '@angular/core';
import { ClientService } from '../api/client-service';

@Injectable({
  providedIn: 'root',
})
export class ClientStoreService {
  constructor(private clientService: ClientService) {}

  // Request
  filters = signal<ClientSearchCriteria>({ keyword: '' });
  page = signal(0);
  size = signal(10);
  sortField = signal('lastName');
  sortDirection = signal<'asc' | 'desc'>('desc');

  // Response
  clients = signal<ClientListDto[]>([]);
  totalPages = signal(0);
  totalElements = signal(0);
  loading = signal(false);

  // COMPUTED
  sort = computed(() => `${this.sortField()},${this.sortDirection()}`);

  // ACTIONS
  updateFilters(partial: Partial<ClientSearchCriteria>): void {
    this.filters.update((current) => ({
      ...current,
      ...partial,
    }));

    this.page.set(0);

    this.loadClients();
  }

  resetFilters(): void {
    this.filters.set({});
  }

  setPage(page: number): void {
    this.page.set(page);

    this.loadClients();
  }

  nextPage(): void {
    this.setPage(this.page() + 1);
  }

  previousPage(): void {
    this.setPage(this.page() - 1);
  }

  setSort(field: string): void {
    if (this.sortField() === field) {
      this.sortDirection.update((dir) => (dir === 'asc' ? 'desc' : 'asc'));
    } else {
      this.sortField.set(field);

      this.sortDirection.set('asc');
    }

    this.loadClients();
  }

  loadClients(): void {
    this.loading.set(true);

    this.clientService.getClients(this.filters(), this.page(), this.size(), this.sort()).subscribe({
      next: (response) => {
        console.log('API RESPONSE', response);
        this.clients.set(response.content);
        console.log('SIGNAL VALUE', this.clients());

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

  isFirstPage() {
    return this.page() === 0;
  }

  isLastPage() {
    return this.page() === this.totalPages() - 1;
  }
}
