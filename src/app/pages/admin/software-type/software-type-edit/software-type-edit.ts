import { Component, inject, OnInit, signal } from '@angular/core';
import { GenericFormPage } from '../../../../components/form/generic-form-page/generic-form-page';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FieldText } from '../../../../components/form/field-text/field-text';
import { SoftwareTypeFormService } from '../../../../services/form-service/software-type-form-service';
import { FieldSelect } from '../../../../components/form/field-select/field-select';

@Component({
  selector: 'app-software-type-edit',
  imports: [GenericFormPage, ReactiveFormsModule, FieldText, FieldSelect],
  templateUrl: './software-type-edit.html',
  styleUrl: './software-type-edit.css',
})
export class SoftwareTypeEdit implements OnInit {
  formBuilder = inject(FormBuilder);
  softwareTypeFormService = inject(SoftwareTypeFormService);
  router = inject(Router);
  route = inject(ActivatedRoute);

  form: FormGroup<SoftwareTypeForm> = this.formBuilder.nonNullable.group({
    designation: this.formBuilder.nonNullable.control('', {
      validators: [Validators.required, Validators.maxLength(127), Validators.minLength(3)],
    }),
  });

  softwareTypeEdit = signal<SoftwareType | undefined>(undefined);
  loading = signal(false);
  error = signal<string | undefined>(undefined);
  routeId = signal<string>('Nouveau type de logiciel');

  isEditMode = signal(false);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) return;

    this.loading.set(true);

    this.softwareTypeFormService.load(Number(id)).subscribe({
      next: (softwareType) => {
        this.isEditMode.set(true);
        this.softwareTypeEdit.set(softwareType);
        this.form.patchValue(this.softwareTypeFormService.mapToForm(softwareType));
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Erreur');
      },
    });
  }

  onSubmit() {
    const dto = this.softwareTypeFormService.mapToDto(
      this.form.getRawValue(),
      this.softwareTypeEdit(),
    );

    this.softwareTypeFormService.save(dto, this.isEditMode()).subscribe({
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
