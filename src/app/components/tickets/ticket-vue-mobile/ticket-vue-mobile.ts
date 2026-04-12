import { Component, Input } from '@angular/core';
import { TicketCard } from '../ticket-card/ticket-card';

@Component({
  selector: 'app-ticket-vue-mobile',
  imports: [TicketCard],
  templateUrl: './ticket-vue-mobile.html',
  styleUrl: './ticket-vue-mobile.css',
})
export class TicketVueMobile {
  @Input() ticketList!: TicketFullLatest[];
}
