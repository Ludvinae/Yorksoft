import { Component, EventEmitter, inject, Input, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ClientStoreService } from '../../../services/business/client-store-service';

/** Searchable client list; the parent page decides when the selection is confirmed. */
@Component({
  selector: 'app-client-search',
  imports: [FormsModule],
  templateUrl: './client-search.html',
  styleUrl: './client-search.css',
})
export class ClientSearch implements OnInit {
  @Input() selectedClient: ClientListDto | null = null;

  @Output()
  clientSelected = new EventEmitter<ClientListDto>();

  storeService = inject(ClientStoreService);

  search = '';
  private searchTimeout: any;

  ngOnInit() {
    // The store outlives this component: keep the input in sync with the filter it still applies
    this.search = this.storeService.filters().keyword ?? '';
    this.storeService.loadClients();
  }

  onSearch(value: string): void {
    this.search = value;
    clearTimeout(this.searchTimeout);
    this.searchTimeout = setTimeout(() => {
      this.storeService.updateFilters({ keyword: value });
    }, 300);
  }

  selectClient(client: ClientListDto): void {
    this.selectedClient = client;
    this.clientSelected.emit(client);
  }
}
