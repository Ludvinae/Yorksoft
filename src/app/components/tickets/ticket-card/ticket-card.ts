import { Component, Input } from '@angular/core';
import { Badge } from '../../badge/badge';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-ticket-card',
  imports: [Badge, DatePipe],
  templateUrl: './ticket-card.html',
  styleUrl: './ticket-card.css',
})
export class TicketCard {
  @Input() ticket!: TicketFullLatest;
}
