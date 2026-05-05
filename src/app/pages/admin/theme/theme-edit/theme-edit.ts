import { Component, inject, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ThemeFormService } from '../../../../services/form-service/theme-form-service';
import { ActivatedRoute, Router } from '@angular/router';
import { FieldSelect } from '../../../../components/form/field-select/field-select';
import { FieldText } from '../../../../components/form/field-text/field-text';
import { GenericFormPage } from '../../../../components/form/generic-form-page/generic-form-page';

@Component({
  selector: 'app-theme-edit',
  imports: [
    FieldSelect,
    FieldText,
    GenericFormPage
  ],
  templateUrl: './theme-edit.html',
  styleUrl: './theme-edit.css',
})
export class ThemeEdit {
  formBuilder = inject(FormBuilder);
  themeFormService = inject(ThemeFormService);
  router = inject(Router);
  route = inject(ActivatedRoute);

  form: FormGroup<ThemeForm> = this.formBuilder.nonNullable.group({
    designation: this.formBuilder.nonNullable.control('', {
      validators: [Validators.required, Validators.maxLength(127), Validators.minLength(3)],
    }),
    description: this.formBuilder.nonNullable.control('', {
      validators: [],
    }),
  });

  themeEdit = signal<Theme | undefined>(undefined);
  loading = signal(false);
  error = signal<string | undefined>(undefined);
  routeId = signal<string>('Nouvelle thématique de dysfonctionnement');

  isEditMode = signal(false);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) return;

    this.loading.set(true);

    this.themeFormService.load(Number(id)).subscribe({
      next: (theme) => {
        this.isEditMode.set(true);
        this.themeEdit.set(theme);
        this.form.patchValue(this.themeFormService.mapToForm(theme));
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Erreur');
      },
    });
  }

  onSubmit() {
    const dto = this.themeFormService.mapToDto(this.form.getRawValue(), this.themeEdit());

    this.themeFormService.save(dto, this.isEditMode()).subscribe({
      next: () => {
        this.loading.set(false);
        this.router.navigateByUrl('/admin/theme');
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Une erreur est survenue');
      },
    });
  }
}
