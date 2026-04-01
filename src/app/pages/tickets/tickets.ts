import { Component, inject, OnInit, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TicketCard } from '../../components/ticket-card/ticket-card';

@Component({
  selector: 'app-tickets',
  imports: [TicketCard],
  templateUrl: './tickets.html',
  styleUrl: './tickets.css',
})
export class Tickets implements OnInit {
  http: HttpClient = inject(HttpClient);
  listeTicket = signal<Ticket[]>([]);

  ngOnInit() {
    this.http
      .get<Ticket[]>('http://localhost:8080/ticket/list')
      .subscribe((listeTicket) => this.listeTicket.set(listeTicket));
  }

}
