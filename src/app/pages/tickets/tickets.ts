import { Component } from '@angular/core';
import {TicketList} from '../../components/tickets/ticket-list/ticket-list';

@Component({
  selector: 'app-tickets',
  imports: [TicketList],
  templateUrl: './tickets.html',
  styleUrl: './tickets.css',
})
export class Tickets {}
