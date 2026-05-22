import { Component, inject, OnInit, signal } from '@angular/core';
import { ClientService } from '../../../services/api/client-service';
import { ClientStoreService } from '../../../services/business/client-store-service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TicketStoreService } from '../../../services/business/ticket-store-service';
import { ClientSearch } from '../../../components/ticket/client-search/client-search';
import { NewTicketForm } from '../../../components/ticket/new-ticket-form/new-ticket-form';

@Component({
  selector: 'app-ticket-new',
  imports: [ReactiveFormsModule, FormsModule, ClientSearch, NewTicketForm],
  templateUrl: './ticket-new.html',
  styleUrl: './ticket-new.css',
})
export class TicketNew {
  selectedClient: ClientListDto | null = null;

  onClientValidated(client: ClientListDto): void {
    this.selectedClient = client;
  }
}
