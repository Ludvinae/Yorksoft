import { Component, inject, Input, OnInit, signal, WritableSignal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { SoftwareTypeService } from '../../../../services/api/software-type-service';
import { DetailPage } from '../../../../components/detail-page/detail-page';

@Component({
  selector: 'app-software-type-detail',
  imports: [RouterLink, DetailPage],
  templateUrl: './software-type-detail.html',
  styleUrl: './software-type-detail.css',
})
export class SoftwareTypeDetail implements OnInit {
  route = inject(ActivatedRoute);
  service = inject(SoftwareTypeService);

  softwareType = signal<SoftwareType | null>(null);
  loading = signal(true);
  error = signal<string | undefined>(undefined);
  label = signal<string | undefined>(undefined);

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.label.set("Type de logiciel #" + id);

    this.service.get(id).subscribe({
      next: (s) => {
        this.softwareType.set(s);
        this.loading.set(false);
      },
      error: () =>
      {this.loading.set(false)
        this.error.set("Impossible de charger les détails.")}
    });
  }


}
