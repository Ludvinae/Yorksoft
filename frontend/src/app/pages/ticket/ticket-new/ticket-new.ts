import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ClientSearch } from '../../../components/ticket/client-search/client-search';
import { NewTicketForm } from '../../../components/ticket/new-ticket-form/new-ticket-form';
import { RouterLink } from '@angular/router';
import { PageHeader } from '../../../components/layout/page-header/page-header';

@Component({
  selector: 'app-ticket-new',
  imports: [ReactiveFormsModule, FormsModule, ClientSearch, NewTicketForm, RouterLink, PageHeader],
  templateUrl: './ticket-new.html',
  styleUrl: './ticket-new.css',
})
export class TicketNew {
  selectedClient: ClientListDto | null = null;
  createdTicket: Ticket | null = null;

  onTicketCreated(ticket: Ticket): void {
    this.createdTicket = ticket;
  }

  onClientValidated(client: ClientListDto): void {
    this.selectedClient = client;
  }
}
