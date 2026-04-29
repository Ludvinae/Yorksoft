import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { SoftwareService } from '../../../services/software-service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-software-edit',
  imports: [ReactiveFormsModule],
  templateUrl: './software-edit.html',
  styleUrl: './software-edit.css',
})
export class SoftwareEdit implements OnInit {
  formBuilder = inject(FormBuilder);
  httpClient = inject(HttpClient);
  route = inject(ActivatedRoute);
  softwareService = inject(SoftwareService);
  router = inject(Router);
  softwareId = signal<number | null>(null);

  listSoftwareType = signal<SoftwareTypeForm[]>([]);
  //softwareEdit = signal<SoftwareForm | null>(null);

  form = this.formBuilder.nonNullable.group({
    name: ['', [Validators.required, Validators.maxLength(127)]],
    description: ['', []],
    idSoftwareType: [null as number | null],
  });

  ngOnInit() {
    this.httpClient
      .get<SoftwareTypeForm[]>('http://localhost:8080/software-type/list')
      .subscribe((listSoftwareType) => this.listSoftwareType.set(listSoftwareType));

    this.route.params.pipe(takeUntilDestroyed())
      .subscribe((param) => {
      if (param['id'] != undefined) {
        const id = +param['id'];

        if (Number.isNaN(id)) {
          alert('ID non valide');
        } else {
          this.softwareId.set(id);

          this.softwareService.get(id).subscribe((software) => {
            this.form.patchValue(software);
          });
        }
      }
    });
  }

  onCreate() {
    if (this.form.valid) {
      const formValue = this.form.getRawValue() as SoftwareForm;
      const id = this.softwareId();

      // EDITION
      if (id) {
        this.softwareService.update(formValue, id).subscribe({
          next: () => {
            alert('Le logiciel a bien été modifié');
            this.form.patchValue(formValue);
          },
          error: this.onError,
        });
      }

      // CREATION
      else {
        this.softwareService.create(formValue).subscribe({
          next: () => {
            alert('Le logiciel a bien été créé');
            this.form.reset();
          },
          error: this.onError,
        });
      }
    }
  }

  onError = (reponse: HttpErrorResponse) => {
    if (reponse.status === 409) {
      alert('Le logiciel existe déja');
    } else {
      alert('Une erreur est survenue');
    }
  };
}
