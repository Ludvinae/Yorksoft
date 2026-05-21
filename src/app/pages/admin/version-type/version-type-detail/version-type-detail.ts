import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RouteBuilderService } from '../../../../services/business/route-builder-service';
import { tap } from 'rxjs';
import { VersionTypeService } from '../../../../services/api/version-type-service';
import { DetailPage } from '../../../../components/detail-page/detail-page';

@Component({
  selector: 'app-version-type-detail',
  imports: [DetailPage],
  templateUrl: './version-type-detail.html',
  styleUrl: './version-type-detail.css',
})
export class VersionTypeDetail implements OnInit {
  route = inject(ActivatedRoute);
  versionTypeService = inject(VersionTypeService);
  routeBuilder = inject(RouteBuilderService);

  versionType = signal<VersionType | null>(null);
  loading = signal(true);
  error = signal<string | undefined>(undefined);
  label = signal<string | undefined>(undefined);

  updateRoute: string | undefined = undefined;

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.label.set('Logiciel #' + id);

    this.updateRoute = this.routeBuilder.getUpdateRoute(id, 'version-type');

    this.versionTypeService
      .get(id)
      .pipe(
        tap((s) => {
          this.versionType.set(s);
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
