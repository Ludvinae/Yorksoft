import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TicketService } from '../../../services/api/ticket-service';
import { RouteBuilderService } from '../../../services/route-builder-service';
import { tap } from 'rxjs';
import { DetailPage } from '../../../components/detail-page/detail-page';

@Component({
  selector: 'app-ticket-detail',
  imports: [DetailPage],
  templateUrl: './ticket-detail.html',
  styleUrl: './ticket-detail.css',
})
export class TicketDetail implements OnInit {
  route = inject(ActivatedRoute);
  ticketService = inject(TicketService);
  routeBuilder = inject(RouteBuilderService);

  ticket = signal<TicketListDto | null>(null);
  loading = signal(true);
  error = signal<string | undefined>(undefined);
  label = signal<string | undefined>(undefined);

  updateRoute: string | undefined = undefined;

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.label.set('Logiciel #' + id);

    this.updateRoute = this.routeBuilder.getUpdateRoute(id, 'ticket');

    this.ticketService
      .getDetail(id)
      .pipe(
        tap((s) => {
          this.ticket.set(s);
        }),
      )
      .subscribe({
        next: () => {
          this.loading.set(false);
        },
        error: () => {
          this.loading.set(false);
          this.error.set('Impossible de charger les détails.');
        },
      });
  }
}
