import { Component, inject } from '@angular/core';
import { TicketStoreService } from '../../services/business/ticket-store-service';

@Component({
  selector: 'app-footer-ticket',
  imports: [],
  templateUrl: './footer-ticket.html',
  styleUrl: './footer-ticket.css',
})
export class FooterTicket {
  protected readonly store = inject(TicketStoreService);

  nextPage(): void {
    this.store.nextPage();
  }

  previousPage(): void {
    this.store.previousPage();
  }
}
