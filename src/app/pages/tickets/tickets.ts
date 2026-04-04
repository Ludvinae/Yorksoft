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
  listeTicket = signal<TicketFullLatest[]>([]);


  ngOnInit() {
    this.http
      .get<TicketFullLatest[]>('http://localhost:8080/ticket/list-full-latest')
      .subscribe((listeTicket) => this.listeTicket.set(listeTicket));
  }

}
