import { Component, Input } from '@angular/core';
import { TableRow } from '../ticket-table-row/table-row';

@Component({
  selector: 'app-ticket-vue-desktop',
  imports: [TableRow],
  templateUrl: './table.html',
  styleUrl: './table.css',
})
export class Table {
  @Input() ticketList!: TicketFullLatest[];
}
