import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { SoftwareService } from '../../../../services/api/software-service';

@Component({
  selector: 'app-software-edit',
  imports: [FormsModule, ReactiveFormsModule, RouterLink],
  templateUrl: './software-edit.html',
  styleUrl: './software-edit.css',
})
export class SoftwareEdit implements OnInit {
  formBuilder = inject(FormBuilder);
  httpClient = inject(HttpClient);
  route = inject(ActivatedRoute);
  softwareService = inject(SoftwareService);
  router = inject(Router);

  //softwareId = signal<number | null>(null);
  listSoftwareType = signal<SoftwareType[]>([]);
  softwareEdit = signal<Software | null>(null);

  form = this.formBuilder.nonNullable.group({
    name: ['', [Validators.required, Validators.maxLength(127)]],
    description: ['', []],
    idSoftwareType: [null as number | null],
  });

  ngOnInit() {
    this.httpClient
      .get<SoftwareType[]>('http://localhost:8080/software-type/list')
      .subscribe((listSoftwareType) => this.listSoftwareType.set(listSoftwareType));

    this.route.params.subscribe((param) => {
      // Si on est en edition
      if (param['id'] != undefined) {
        const id = +param['id'];

        if (Number.isNaN(id)) {
          alert('ID non valide'); // peut etre remplacer par un message, ou une redirection vers une page 404
        } else {
          this.softwareService.get(id).subscribe((software) => {
            this.form.patchValue(software);
            this.softwareEdit.set(software);
          });
        }
      }
    });
  }

  onCreate() {
    if (this.form.valid) {
      // const value = this.form.value;
      //
      // const software: Software = {
      //   ...value,
      //   idSoftware: this.softwareEdit()?.idSoftware ?? 0,
      //   idSoftwareType: value.idSoftwareType!
      // }
      // SI on est en edition
      if (this.softwareEdit()) {
        this.softwareService
          .update(this.form.value as Software, this.softwareEdit()!.idSoftware)
          .subscribe({
            next: (newSoftware) => {
              alert('Le logiciel a bien été modifié');
              // Naviguer vers l'accueil
              this.router.navigateByUrl("/admin/software");
            },
            error: this.onError,
          });
      }
      //Si on est en création
      else {
        this.softwareService.create(this.form.value as Software).subscribe({
          next: (newSoftware) => {
            alert('Le logiciel a bien été crée');
            // Vider le formulaire
            this.form.reset();
          },
          error: this.onError,
        });
      }
    }
  }

  onError = (reponse: Response) => {
    if (reponse.status === 409) {
      alert('Le logiciel existe déja');
    } else {
      alert('Une erreur est survenue');
    }
  };
}
