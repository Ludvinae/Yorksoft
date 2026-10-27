import { Component, inject, Input } from '@angular/core';
import { Router } from '@angular/router';
import { TicketStoreService } from '../../../../services/business/ticket-store-service';
import { TicketSearchCriteria } from '../../../../models/search/TicketSearchCriteria';

@Component({
  selector: 'app-kpi-card',
  imports: [],
  templateUrl: './kpi-card.html',
  styleUrl: './kpi-card.css',
})
export class KpiCard {
  @Input({ required: true }) title!: string;
  /** Shown as "-" when null (e.g. no data yet). */
  @Input({ required: true }) value!: string | number | null;
  /** Unit appended to the value (e.g. "m"), not shown when value is null. */
  @Input() suffix = '';
  @Input() icon?: string;
  /** When set, clicking the card opens the ticket list with these filters; otherwise the card is not clickable. */
  @Input() filters?: TicketSearchCriteria;
  @Input() colorClass = '';

  private router = inject(Router);
  private ticketStore = inject(TicketStoreService);

  navigateToTickets(): void {
    if (!this.filters) return;

    this.ticketStore.setFilters(this.filters);

    this.router.navigate(['/ticket']);
  }
}
