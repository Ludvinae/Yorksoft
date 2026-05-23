import { Component, inject, Input } from '@angular/core';
import { Router } from '@angular/router';
import { TicketStoreService } from '../../../services/business/ticket-store-service';
import { TicketSearchCriteria } from '../../../models/page/TicketSearchCriteria';

@Component({
  selector: 'app-kpi-card',
  imports: [],
  templateUrl: './kpi-card.html',
  styleUrl: './kpi-card.css',
})
export class KpiCard {
  @Input({ required: true }) title!: string;
  @Input({ required: true }) value!: string | number;
  @Input({ required: true }) icon!: string;
  @Input() filters: TicketSearchCriteria = {};
  @Input() colorClass = '';

  private router = inject(Router);
  private ticketStore = inject(TicketStoreService);

  navigateToTickets(): void {
    this.ticketStore.setFilters(this.filters);

    this.router.navigate(['/ticket']);
  }
}
