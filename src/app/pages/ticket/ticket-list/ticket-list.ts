import { Component, inject, OnInit } from '@angular/core';
import { TicketService } from '../../../services/api/ticket-service';
import { TitleBar } from '../../../components/title-bar/title-bar/title-bar';
import { TicketStoreService } from '../../../services/ticket-store-service';
import { FooterTicket } from '../../../components/footer-ticket/footer-ticket';

@Component({
  selector: 'app-ticket-list',
  imports: [TitleBar, FooterTicket],
  templateUrl: './ticket-list.html',
  styleUrl: './ticket-list.css',
})
export class TicketList implements OnInit {
  ticketService = inject(TicketService);
  storeService = inject(TicketStoreService);

  ngOnInit() {
    this.storeService.loadTickets();
  }

  onDelete(id: number) {
    this.ticketService.delete(id).subscribe({
      next: () => {
        alert('Supprimé');
        this.storeService.loadTickets();
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
}
