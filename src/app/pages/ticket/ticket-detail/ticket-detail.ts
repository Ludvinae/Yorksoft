import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { TicketService } from '../../../services/api/ticket-service';
import { RouteBuilderService } from '../../../services/business/route-builder-service';
import { tap } from 'rxjs';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-ticket-detail',
  imports: [RouterLink, DatePipe],
  templateUrl: './ticket-detail.html',
  styleUrl: './ticket-detail.css',
})
export class TicketDetail implements OnInit {
  route = inject(ActivatedRoute);
  ticketService = inject(TicketService);
  routeBuilder = inject(RouteBuilderService);

  ticket = signal<TicketDetailFullDto | null>(null);

  detail = computed(() => this.ticket()?.details);
  comments = computed(() => this.ticket()?.comments ?? []);
  knowledge = computed(() => this.ticket()?.knowledge);
  articles = computed(() => this.ticket()?.articles ?? []);

  loading = signal(true);
  error = signal<string | undefined>(undefined);
  label = signal<string | undefined>(undefined);

  updateRoute: string | undefined = undefined;

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.label.set('Ticket #' + id);

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
