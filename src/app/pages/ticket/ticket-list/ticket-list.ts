import { Component, inject, OnInit } from '@angular/core';
import { GenericList } from '../../../components/list/generic-list/generic-list';
import { TicketService } from '../../../services/api/ticket-service';
import { TitleBar } from '../../../components/title-bar/title-bar/title-bar';
import { TicketStoreService } from '../../../services/ticket-store-service';

@Component({
  selector: 'app-ticket-list',
  imports: [GenericList, TitleBar],
  templateUrl: './ticket-list.html',
  styleUrl: './ticket-list.css',
})
export class TicketList implements OnInit {
  ticketService = inject(TicketService);
  storeService = inject(TicketStoreService);

  ticketList = this.storeService.tickets;


  ngOnInit() {
    this.storeService.loadTickets()
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

}
