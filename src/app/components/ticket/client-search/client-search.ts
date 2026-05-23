import { Component, EventEmitter, inject, OnInit, Output } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ClientStoreService } from '../../../services/business/client-store-service';

@Component({
  selector: 'app-client-search',
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './client-search.html',
  styleUrl: './client-search.css',
})
export class ClientSearch implements OnInit {
  @Output()
  clientValidated = new EventEmitter<ClientListDto>();

  storeService = inject(ClientStoreService);

  search = '';
  private searchTimeout: any;

  selectedClient: ClientListDto | null = null;

  ngOnInit() {
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
  }

  continue(): void {
    if (!this.selectedClient) return;

    this.clientValidated.emit(this.selectedClient);
  }
}
