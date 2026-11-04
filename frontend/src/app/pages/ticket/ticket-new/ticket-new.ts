import { Component, inject, signal } from '@angular/core';
import { ClientSearch } from '../../../components/ticket/client-search/client-search';
import { NewTicketForm } from '../../../components/ticket/new-ticket-form/new-ticket-form';
import { Router, RouterLink } from '@angular/router';
import { PageHeader } from '../../../components/layout/page-header/page-header';
import { AuthService } from '../../../services/business/auth-service';
import { TicketService } from '../../../services/api/ticket-service';

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
  private router = inject(Router);
  private auth = inject(AuthService);
  private ticketService = inject(TicketService);

  /** Client highlighted in the search list (step 1). */
  selectedClient: ClientListDto | null = null;
  /** Client confirmed with "Continuer" — moves the wizard to step 2. */
  confirmedClient: ClientListDto | null = null;
  createdTicket: Ticket | null = null;

  selfAssigning = signal(false);
  selfAssignError = signal<string | null>(null);

  /**
   * A freshly created ticket is open, unassigned and created by the current user,
   * so only the role is left to check (the backend re-checks everything).
   */
  get canSelfAssign(): boolean {
    return this.auth.jwtInfo()?.role === 'TECHNICIAN';
  }

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

  /** Assign the new ticket to the current technician, take it in charge, and open it. */
  selfAssign(): void {
    if (!this.createdTicket || this.selfAssigning()) return;

    const idTicket = this.createdTicket.idTicket;
    this.selfAssigning.set(true);
    this.selfAssignError.set(null);

    this.ticketService.selfAssign(idTicket).subscribe({
      next: () => {
        this.selfAssigning.set(false);
        this.router.navigate(['/ticket', idTicket]);
      },
      error: () => {
        this.selfAssigning.set(false);
        this.selfAssignError.set('Impossible de vous attribuer ce ticket.');
      },
    });
  }

  /** Restart the wizard. Navigating to /ticket/create wouldn't work: it's the current route, so Angular keeps this component as is. */
  reset(): void {
    this.selectedClient = null;
    this.confirmedClient = null;
    this.createdTicket = null;
    this.selfAssignError.set(null);
  }
}
