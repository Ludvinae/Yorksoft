import { Component, inject, OnInit } from '@angular/core';
import { GenericList } from '../../../components/list/generic-list/generic-list';
import { Title } from '../../../components/list/title/title';
import { TicketService } from '../../../services/api/ticket-service';
import { TitleBar } from '../../../components/title-bar/title-bar/title-bar';

@Component({
  selector: 'app-ticket-list',
  imports: [GenericList, TitleBar],
  templateUrl: './ticket-list.html',
  styleUrl: './ticket-list.css',
})
export class TicketList implements OnInit {
  ticketService = inject(TicketService);

  ticketList = this.ticketService.ticketDetail;

  params = ""

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

  onClick(value: string) {
    this.params = value;
  }
}
