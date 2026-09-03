import { Component, inject, OnInit, signal } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { GenericFormPage } from '../../../../components/form/generic-form-page/generic-form-page';
import { ActivatedRoute, Router } from '@angular/router';
import { VersionTypeService } from '../../../../services/api/version-type-service';
import { SoftwareService } from '../../../../services/api/software-service';
import { FieldText } from '../../../../components/form/field-text/field-text';
import { validDate } from '../../../../validators/validDate';
import { FieldSelect } from '../../../../components/form/field-select/field-select';
import { toSignal } from '@angular/core/rxjs-interop';
import { VersionFormService } from '../../../../services/form-service/version-form-service';

@Component({
  selector: 'app-version-edit',
  imports: [FormsModule, GenericFormPage, ReactiveFormsModule, FieldText, FieldSelect],
  templateUrl: './version-edit.html',
  styleUrl: './version-edit.css',
})
export class VersionEdit implements OnInit {
  formBuilder = inject(FormBuilder);
  versionFormService = inject(VersionFormService);
  router = inject(Router);
  route = inject(ActivatedRoute);
  softwareService = inject(SoftwareService);
  versionTypeService = inject(VersionTypeService);

  form: FormGroup<VersionForm> = this.formBuilder.nonNullable.group({
    versionNumber: this.formBuilder.nonNullable.control('', {
      validators: [Validators.required, Validators.maxLength(127)],
    }),
    publicationDate: this.formBuilder.nonNullable.control('', {
      validators: [Validators.required, validDate],
    }),
    idVersionType: this.formBuilder.nonNullable.control(0, { validators: [Validators.required] }),
    idSoftware: this.formBuilder.nonNullable.control(0, { validators: [Validators.required] }),
  });

  versionEdit = signal<Version | undefined>(undefined);
  loading = signal(false);
  error = signal<string | undefined>(undefined);
  routeId = signal<string>('Nouvelle version de logiciel');

  listSoftware = toSignal(this.softwareService.getAll(), { initialValue: [] });
  listVersionType = toSignal(this.versionTypeService.getAll(), { initialValue: [] });

  isEditMode = signal(false);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) return;

    this.loading.set(true);

    this.versionFormService.load(Number(id)).subscribe({
      next: (version) => {
        this.isEditMode.set(true);
        this.versionEdit.set(version);
        this.form.patchValue(this.versionFormService.mapToForm(version));
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Erreur');
      },
    });
  }

  onSubmit() {
    const dto = this.versionFormService.mapToDto(this.form.getRawValue(), this.versionEdit());

    this.versionFormService.save(dto, this.isEditMode()).subscribe({
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
}
