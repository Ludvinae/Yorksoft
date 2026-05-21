import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UrgencyService } from '../../../../services/api/urgency-service';
import { RouteBuilderService } from '../../../../services/business/route-builder-service';
import { tap } from 'rxjs';
import { DetailPage } from '../../../../components/detail-page/detail-page';

@Component({
  selector: 'app-urgency-detail',
  imports: [DetailPage],
  templateUrl: './urgency-detail.html',
  styleUrl: './urgency-detail.css',
})
export class UrgencyDetail implements OnInit {
  route = inject(ActivatedRoute);
  urgencyService = inject(UrgencyService);
  routeBuilder = inject(RouteBuilderService);

  urgency = signal<Urgency | null>(null);
  loading = signal(true);
  error = signal<string | undefined>(undefined);
  label = signal<string | undefined>(undefined);

  updateRoute: string | undefined = undefined;

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.label.set('Logiciel #' + id);

    this.updateRoute = this.routeBuilder.getUpdateRoute(id, 'urgency');

    this.urgencyService
      .get(id)
      .pipe(
        tap((s) => {
          this.urgency.set(s);
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
