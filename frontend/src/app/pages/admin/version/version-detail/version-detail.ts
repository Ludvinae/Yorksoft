import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VersionService } from '../../../../services/api/version-service';
import { RouteBuilderService } from '../../../../services/business/route-builder-service';
import { tap } from 'rxjs';
import { DetailPage } from '../../../../components/detail-page/detail-page';

@Component({
  selector: 'app-version-detail',
  imports: [DetailPage],
  templateUrl: './version-detail.html',
  styleUrl: './version-detail.css',
})
export class VersionDetail implements OnInit {
  route = inject(ActivatedRoute);
  versionService = inject(VersionService);
  routeBuilder = inject(RouteBuilderService);

  version = signal<VersionDetailDto | null>(null);
  loading = signal(true);
  error = signal<string | undefined>(undefined);
  label = signal<string | undefined>(undefined);

  updateRoute: string | undefined = undefined;

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.label.set('Logiciel #' + id);

    this.updateRoute = this.routeBuilder.getUpdateRoute(id, 'version');

    this.versionService
      .getDetail(id)
      .pipe(
        tap((s) => {
          this.version.set(s);
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
