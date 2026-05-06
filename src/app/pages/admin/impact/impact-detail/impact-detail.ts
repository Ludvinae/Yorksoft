import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ImpactService } from '../../../../services/api/impact-service';
import { RouteBuilderService } from '../../../../services/route-builder-service';
import { tap } from 'rxjs';
import { DetailPage } from '../../../../components/detail-page/detail-page';

@Component({
  selector: 'app-impact-detail',
  imports: [DetailPage],
  templateUrl: './impact-detail.html',
  styleUrl: './impact-detail.css',
})
export class ImpactDetail implements OnInit {
  route = inject(ActivatedRoute);
  impactService = inject(ImpactService);
  routeBuilder = inject(RouteBuilderService);

  impact = signal<Impact | null>(null);
  loading = signal(true);
  error = signal<string | undefined>(undefined);
  label = signal<string | undefined>(undefined);

  updateRoute: string | undefined = undefined;

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.label.set('Logiciel #' + id);

    this.updateRoute = this.routeBuilder.getUpdateRoute(id, 'impact');

    this.impactService
      .get(id)
      .pipe(
        tap((s) => {
          this.impact.set(s);
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
