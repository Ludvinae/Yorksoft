import { Component, inject, signal } from '@angular/core';
import { GenericFormPage } from '../../../../components/form/generic-form-page/generic-form-page';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { SoftwareTypeService } from '../../../../services/api/software-type-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-software-type-edit',
  imports: [GenericFormPage, ReactiveFormsModule],
  templateUrl: './software-type-edit.html',
  styleUrl: './software-type-edit.css',
})
export class SoftwareTypeEdit {
  formBuilder = inject(FormBuilder);
  softwareTypeService = inject(SoftwareTypeService);
  router = inject(Router);

  form = this.formBuilder.nonNullable.group({
    designation: ['', Validators.required],
  });

  softwareTypeEdit = signal<SoftwareType | null>(null);
  loading = signal(false);
  error = signal<string | undefined>(undefined);

  onSubmit() {
    if (this.form.invalid) return;

    this.loading.set(true);

    const value = this.form.getRawValue();

    const type: SoftwareType = {
      ...value,
      idSoftwareType: this.softwareTypeEdit()?.idSoftwareType ?? 0,
    };

    const request = this.softwareTypeEdit()
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
}
