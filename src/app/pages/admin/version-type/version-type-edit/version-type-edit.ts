import { Component, inject, OnInit, signal } from '@angular/core';
import {FieldText} from "../../../../components/form/field-text/field-text";
import { GenericFormPage } from '../../../../components/form/generic-form-page/generic-form-page';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { VersionTypeFormService } from '../../../../services/form-service/version-type-form-service';

@Component({
  selector: 'app-version-type-edit',
  imports: [FieldText, GenericFormPage],
  templateUrl: './version-type-edit.html',
  styleUrl: './version-type-edit.css',
})
export class VersionTypeEdit implements OnInit {
  formBuilder = inject(FormBuilder);
  versionTypeFormService = inject(VersionTypeFormService);
  router = inject(Router);
  route = inject(ActivatedRoute);

  form: FormGroup<VersionTypeForm> = this.formBuilder.nonNullable.group({
    designation: this.formBuilder.nonNullable.control('', {
      validators: [Validators.required, Validators.maxLength(127), Validators.minLength(1)],
    }),
    urgencyMalus: this.formBuilder.nonNullable.control(0, {
      validators: [Validators.required],
    }),
  });

  versionTypeEdit = signal<VersionType | undefined>(undefined);
  loading = signal(false);
  error = signal<string | undefined>(undefined);
  routeId = signal<string>('Nouvelle version de logiciel');

  isEditMode = signal(false);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) return;

    this.loading.set(true);

    this.versionTypeFormService.load(Number(id)).subscribe({
      next: (type) => {
        this.isEditMode.set(true);
        this.versionTypeEdit.set(type);
        this.form.patchValue(this.versionTypeFormService.mapToForm(type));
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Erreur');
      },
    });
  }

  onSubmit() {
    const dto = this.versionTypeFormService.mapToDto(
      this.form.getRawValue(),
      this.versionTypeEdit());

    this.versionTypeFormService.save(dto, this.isEditMode()).subscribe({
      next: () => {
        this.loading.set(false);
        this.router.navigateByUrl('/admin/version-type');
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Une erreur est survenue');
      },
    });
  }
}
