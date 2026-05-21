import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { StatusService } from '../../../../services/api/status-service';
import { RouteBuilderService } from '../../../../services/business/route-builder-service';
import { tap } from 'rxjs';
import { DetailPage } from '../../../../components/detail-page/detail-page';

@Component({
  selector: 'app-status-detail',
  imports: [DetailPage],
  templateUrl: './status-detail.html',
  styleUrl: './status-detail.css',
})
export class StatusDetail implements OnInit {
  route = inject(ActivatedRoute);
  statusService = inject(StatusService);
  routeBuilder = inject(RouteBuilderService);

  status = signal<Status | null>(null);
  loading = signal(true);
  error = signal<string | undefined>(undefined);
  label = signal<string | undefined>(undefined);

  updateRoute: string | undefined = undefined;

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.label.set('Logiciel #' + id);

    this.updateRoute = this.routeBuilder.getUpdateRoute(id, 'status');

    this.statusService
      .get(id)
      .pipe(
        tap((s) => {
          this.status.set(s);
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
