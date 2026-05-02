import { Component, inject, OnInit, signal } from '@angular/core';
import { GenericFormPage } from '../../../../components/form/generic-form-page/generic-form-page';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { SoftwareTypeService } from '../../../../services/api/software-type-service';
import { ActivatedRoute, Router } from '@angular/router';
import { VersionService } from '../../../../services/api/version-service';
import { SoftwareService } from '../../../../services/api/software-service';
import { VersionTypeService } from '../../../../services/api/version-type-service';
import { validDate } from '../../../../validators/validDate';
import { FieldText } from '../../../../components/form/field-text/field-text';

@Component({
  selector: 'app-software-type-edit',
  imports: [GenericFormPage, ReactiveFormsModule, FieldText],
  templateUrl: './software-type-edit.html',
  styleUrl: './software-type-edit.css',
})
export class SoftwareTypeEdit implements OnInit {
  formBuilder = inject(FormBuilder);
  softwareTypeService = inject(SoftwareTypeService);
  router = inject(Router);
  route = inject(ActivatedRoute);

  form = this.formBuilder.nonNullable.group({
    designation: ['', [Validators.required, Validators.maxLength(127), Validators.minLength(3)]],
  });

  softwareTypeEdit = signal<SoftwareType | null>(null);
  loading = signal(false);
  error = signal<string | undefined>(undefined);
  routeId = signal<string>('Nouvelle version de logiciel');

  isEditMode = signal(false);

  ngOnInit() {
    // Récupération de l'id
    const id = this.route.snapshot.paramMap.get('id');

    if (!id) return;

    //Gestion du mode edition
    this.routeId.set('Version #' + id);

    const numericId = Number(id);

    if (Number.isNaN(numericId)) {
      this.error.set('ID invalide');
      return;
    }

    this.error.set(undefined);
    this.loading.set(true);

    this.softwareTypeService.get(numericId).subscribe({
      next: (type) => {
        this.isEditMode.set(true);
        this.softwareTypeEdit.set(type);

        // Remplissage du formulaire
        this.form.patchValue({
          designation: type.designation,
        });

        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Erreur lors du chargement');
      },
    });
  }

  onSubmit() {
    if (this.form.invalid) return;

    this.error.set(undefined);
    this.loading.set(true);

    const value = this.form.getRawValue();

    const type: SoftwareType = {
      ...value,
      idSoftwareType: this.softwareTypeEdit()?.idSoftwareType ?? 0,
    };

    const request = this.isEditMode()
      ? this.softwareTypeService.update(type, type.idSoftwareType)
      : this.softwareTypeService.create(type);

    request.subscribe({
      next: () => {
        this.loading.set(false);
        this.router.navigateByUrl('/admin/software-type');
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Une erreur est survenue');
      },
    });
  }

  formatDate(date: string | Date): string {
    const d = new Date(date);
    return d.toISOString().split('T')[0]; // YYYY-MM-DD
  }
}
