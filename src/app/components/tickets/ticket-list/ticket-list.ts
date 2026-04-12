import { Component, inject, OnInit, Signal, signal } from '@angular/core';
import { TicketService } from '../../../services/ticket-service';
import { toSignal } from '@angular/core/rxjs-interop';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { map } from 'rxjs';
import { TicketVueMobile } from '../ticket-vue-mobile/ticket-vue-mobile';
import { Table } from '../ticket-vue-desktop/table';

@Component({
  selector: 'app-ticket-list',
  imports: [TicketVueMobile, Table],
  templateUrl: './ticket-list.html',
  styleUrl: './ticket-list.css',
})

/**
 * S'occupe de faire le switch entre la vue mobile et desktop
 * Et envoie la liste de ticket au bon composant
 * (ticket-vue-mobile ou ticket-vue-desktop)
 */
export class TicketList {
  private ticketService = inject(TicketService);
  private breakpointObserver = inject(BreakpointObserver);

  tickets = toSignal(this.ticketService.getTickets(), { initialValue: [] });

  isMobile = toSignal(
    this.breakpointObserver.observe([Breakpoints.Handset]).pipe(map((result) => result.matches)),
    { initialValue: false },
  );
}
