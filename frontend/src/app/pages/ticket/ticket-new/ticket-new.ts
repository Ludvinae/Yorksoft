import { Component } from '@angular/core';
import { ClientSearch } from '../../../components/ticket/client-search/client-search';
import { NewTicketForm } from '../../../components/ticket/new-ticket-form/new-ticket-form';
import { RouterLink } from '@angular/router';
import { PageHeader } from '../../../components/layout/page-header/page-header';

/**
 * Two-step wizard: pick a client, then fill the ticket form.
 * The page owns the wizard state so step actions can sit in the page header.
 */
@Component({
  selector: 'app-ticket-new',
  imports: [ClientSearch, NewTicketForm, RouterLink, PageHeader],
  templateUrl: './ticket-new.html',
  styleUrl: './ticket-new.css',
})
export class TicketNew {
  /** Client highlighted in the search list (step 1). */
  selectedClient: ClientListDto | null = null;
  /** Client confirmed with "Continuer" — moves the wizard to step 2. */
  confirmedClient: ClientListDto | null = null;
  createdTicket: Ticket | null = null;

  onClientSelected(client: ClientListDto): void {
    this.selectedClient = client;
  }

  continue(): void {
    if (!this.selectedClient) return;

    this.confirmedClient = this.selectedClient;
  }

  /** Back to step 1; the previous selection stays highlighted. */
  changeClient(): void {
    this.confirmedClient = null;
  }

  onTicketCreated(ticket: Ticket): void {
    this.createdTicket = ticket;
  }

  /** Restart the wizard. Navigating to /ticket/create wouldn't work: it's the current route, so Angular keeps this component as is. */
  reset(): void {
    this.selectedClient = null;
    this.confirmedClient = null;
    this.createdTicket = null;
  }
}
