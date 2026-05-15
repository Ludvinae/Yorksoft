import { Component, inject, OnInit } from '@angular/core';
import { GenericList } from '../../../components/list/generic-list/generic-list';
import { Title } from '../../../components/list/title/title';
import { TicketService } from '../../../services/api/ticket-service';

@Component({
  selector: 'app-ticket-list',
  imports: [GenericList, Title],
  templateUrl: './ticket-list.html',
  styleUrl: './ticket-list.css',
})
export class TicketList implements OnInit {
  ticketService = inject(TicketService);

  ticketList = this.ticketService.ticketDetail;

  ngOnInit() {
    this.ticketService.getAllDto().subscribe();
  }

  onDelete(id: number) {
    this.ticketService.delete(id).subscribe({
      next: () => {
        alert('Supprimé');
        this.ticketService.getAllDto().subscribe();
      },
      error: () => alert('Erreur'),
    });
  }
}
