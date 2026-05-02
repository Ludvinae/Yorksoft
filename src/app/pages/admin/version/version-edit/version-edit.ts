import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { GenericFormPage } from '../../../../components/form/generic-form-page/generic-form-page';
import { ActivatedRoute, Router } from '@angular/router';
import { VersionService } from '../../../../services/api/version-service';
import { VersionTypeService } from '../../../../services/api/version-type-service';
import { SoftwareService } from '../../../../services/api/software-service';
import { FieldText } from '../../../../components/form/field-text/field-text';
import { validDate } from '../../../../validators/validDate';
import { FieldSelect } from '../../../../components/form/field-select/field-select';

@Component({
  selector: 'app-version-edit',
  imports: [FormsModule, GenericFormPage, ReactiveFormsModule, FieldText, FieldSelect],
  templateUrl: './version-edit.html',
  styleUrl: './version-edit.css',
})
export class VersionEdit implements OnInit {
  formBuilder = inject(FormBuilder);
  versionService = inject(VersionService);
  router = inject(Router);
  route = inject(ActivatedRoute);
  softwareService = inject(SoftwareService);
  versionTypeService = inject(VersionTypeService);

  form = this.formBuilder.nonNullable.group({
    versionNumber: ['', [Validators.required, Validators.maxLength(127)]],
    publicationDate: ['' as string, [Validators.required, validDate]],
    idVersionType: [0, Validators.required],
    idSoftware: [0, Validators.required],
  });

  versionEdit = signal<Version | null>(null);
  loading = signal(false);
  error = signal<string | undefined>(undefined);
  routeId = signal<string>('Nouvelle version de logiciel');

  listSoftware = signal<Software[]>([]);
  listVersionType = signal<VersionType[]>([]);

  isEditMode = signal(false);

  ngOnInit() {
    // Chargement des listes pour les champs select
    this.softwareService.getAll().subscribe((software) => {
      this.listSoftware.set(software);
    });

    this.versionTypeService.getAll().subscribe((type) => {
      this.listVersionType.set(type);
    });

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

    this.versionService.get(numericId).subscribe({
      next: (version) => {
        this.isEditMode.set(true);
        this.versionEdit.set(version);

        // Remplissage du formulaire
        this.form.patchValue({
          versionNumber: version.versionNumber,
          publicationDate: this.formatDate(version.publicationDate),
          idVersionType: version.idVersionType,
          idSoftware: version.idSoftware,
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

    if (value.idVersionType == null || value.idSoftware == null) {
      this.error.set('Champs obligatoires manquants');
      this.loading.set(false);
      return;
    }

    const type: Version = {
      ...value,
      idVersion: this.versionEdit()?.idVersion ?? 0,
      publicationDate: new Date(value.publicationDate),
    };

    const request = this.isEditMode()
      ? this.versionService.update(type, type.idVersion)
      : this.versionService.create(type);

    request.subscribe({
      next: () => {
        this.loading.set(false);
        this.router.navigateByUrl('/admin/version');
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
