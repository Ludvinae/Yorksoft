import { Component, inject, OnInit } from '@angular/core';
import {TicketList} from '../../components/tickets/ticket-list/ticket-list';
import { Auth } from '../../services/auth';

@Component({
  selector: 'app-tickets',
  imports: [TicketList],
  templateUrl: './tickets.html',
  styleUrl: './tickets.css',
})
export class Tickets {

}
