import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SoftwareService } from '../../../../services/api/software-service';
import { SoftwareTypeService } from '../../../../services/api/software-type-service';
import { DetailPage } from '../../../../components/detail-page/detail-page';
import { RouteBuilderService } from '../../../../services/route-builder-service';
import { switchMap, tap } from 'rxjs';

@Component({
  selector: 'app-software-detail',
  imports: [DetailPage],
  templateUrl: './software-detail.html',
  styleUrl: './software-detail.css',
})
export class SoftwareDetail implements OnInit {
  route = inject(ActivatedRoute);
  softwareService = inject(SoftwareService);
  softwareTypeService = inject(SoftwareTypeService);
  routeBuilder = inject(RouteBuilderService);

  software = signal<Software | null>(null);
  loading = signal(true);
  error = signal<string | undefined>(undefined);
  label = signal<string | undefined>(undefined);
  softwareType = signal<SoftwareType | null>(null);


  updateRoute: string | undefined = undefined;

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.label.set('Logiciel #' + id);

    this.updateRoute = this.routeBuilder.getUpdateRoute(id, 'software');

    this.softwareService
      .get(id)
      .pipe(
        tap((s) => {
          this.software.set(s);
        }),
        switchMap((s) => {
          if (s.idSoftwareType == null) {
            throw new Error('Type de logiciel manquant');
          }
          return this.softwareTypeService.get(s.idSoftwareType)
        }),
      )
      .subscribe({
        next: (type) => {
          this.softwareType.set(type);
          this.loading.set(false);
        },
        error: () => {
          this.loading.set(false);
          this.error.set('Impossible de charger les détails.');
        },
    });


  }
}
