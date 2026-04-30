import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-software-detail',
  imports: [RouterLink],
  templateUrl: './software-detail.html',
  styleUrl: './software-detail.css',
})
export class SoftwareDetail {
  route = inject(ActivatedRoute);
  httpClient = inject(HttpClient);
  softwareSignal = signal<Software | null>(null);
  typeSignal = signal<SoftwareType | null>(null);

  ngOnInit() {
    this.route.params.subscribe((params) => {
      const id = +params['id'];

      if (Number.isNaN(id)) {
        alert('id non valide');
      } else {
        this.httpClient
          .get<Software>(`http://localhost:8080/software/` + id)
          .subscribe((software) => {
            this.softwareSignal.set(software);
          });
        if (this.softwareSignal()?.idSoftwareType) {
          this.httpClient
            .get<SoftwareType>(
              `http://localhost:8080/software-type/` + this.softwareSignal()!.idSoftwareType,
            )
            .subscribe((type) => {
              this.typeSignal.set(type);
            });
        }
      }
    });
  }
}
