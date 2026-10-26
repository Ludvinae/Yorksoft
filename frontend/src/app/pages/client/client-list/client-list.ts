import { Component, inject, OnInit } from '@angular/core';
import { ClientStoreService } from '../../../services/business/client-store-service';
import { ClientService } from '../../../services/api/client-service';
import { FooterClient } from '../../../components/footer-client/footer-client';
import { TitleBar } from '../../../components/title-bar/title-bar/title-bar';

@Component({
  selector: 'app-client-list',
  imports: [FooterClient, TitleBar],
  templateUrl: './client-list.html',
  styleUrl: './client-list.css',
})
export class ClientList implements OnInit {
  clientService = inject(ClientService);
  storeService = inject(ClientStoreService);

  buttonState = 'all';

  filters: ClientSearchCriteria = {};

  ngOnInit() {
    this.storeService.loadClients();
  }

  onDelete(id: number) {
    this.clientService.delete(id).subscribe({
      next: () => {
        alert('Supprimé');
        this.storeService.loadClients();
      },
      error: () => alert('Erreur'),
    });
  }

  isSorted(field: string): boolean {
    return this.storeService.sortField() === field;
  }

  isAsc(): boolean {
    return this.storeService.sortDirection() === 'asc';
  }

  setFilter(filter: 'all' | 'asking' | 'important'): void {
    this.filters = {};

    switch (filter) {
      case 'asking':
        this.storeService.resetFilters();
        this.storeService.updateFilters({ hasOpenTicket: true });
        this.buttonState = 'asking';
        break;

      case 'important':
        this.storeService.resetFilters();
        this.storeService.updateFilters({ importanceGreaterThan: 1 });
        this.buttonState = 'important';
        break;

      case 'all':
        this.storeService.resetFilters();
        this.storeService.updateFilters({});
        this.buttonState = 'all';
        break;
    }
  }

  onSearch(value: string): void {
    this.storeService.updateFilters({ keyword: value });
  }
}
