import { Component, Input } from '@angular/core';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'tr[app-ticket-table-row]',
  imports: [DatePipe],
  templateUrl: './table-row.html',
  styleUrl: './table-row.css',
})
export class TableRow {
  @Input() ticket!: TicketFullLatest;
}
