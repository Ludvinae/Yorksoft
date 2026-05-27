import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TicketService } from '../../../services/api/ticket-service';
import { RouteBuilderService } from '../../../services/business/route-builder-service';
import { tap } from 'rxjs';
import { DetailPage } from '../../../components/detail-page/detail-page';
import {TicketDetailDto} from '../../../models/details/TicketDetailDto';

@Component({
  selector: 'app-ticket-detail',
  imports: [],
  templateUrl: './ticket-detail.html',
  styleUrl: './ticket-detail.css',
})
export class TicketDetail implements OnInit {
  route = inject(ActivatedRoute);
  ticketService = inject(TicketService);
  routeBuilder = inject(RouteBuilderService);

  //ticket = signal<TicketDetailFullDto | null>(null);
  detail = signal<TicketDetailDto | null>(null);
  comments = signal<TicketDetailComment[] | null>(null);
  knowledge = signal<TicketDetailKnowledge | null>(null);
  articles = signal<TicketDetailArticle[] | null>(null);

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
          this.detail.set(s.details);
          this.comments.set(s.comments);
          this.knowledge.set(s.knowledge);
          this.articles.set(s.articles);
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
