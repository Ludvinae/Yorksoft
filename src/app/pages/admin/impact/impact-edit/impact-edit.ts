import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ImpactFormService } from '../../../../services/form-service/impact-form-service';
import { ActivatedRoute, Router } from '@angular/router';
import { FieldText } from '../../../../components/form/field-text/field-text';
import { GenericFormPage } from '../../../../components/form/generic-form-page/generic-form-page';

@Component({
  selector: 'app-impact-edit',
  imports: [
    FieldText,
    GenericFormPage
  ],
  templateUrl: './impact-edit.html',
  styleUrl: './impact-edit.css',
})
export class ImpactEdit implements OnInit {
  formBuilder = inject(FormBuilder);
  impactFormService = inject(ImpactFormService);
  router = inject(Router);
  route = inject(ActivatedRoute);

  form: FormGroup<ImpactForm> = this.formBuilder.nonNullable.group({
    designation: this.formBuilder.nonNullable.control('', {
      validators: [Validators.required, Validators.maxLength(63), Validators.minLength(1)],
    }),
    priorityFactor: this.formBuilder.nonNullable.control(0, {
      validators: [Validators.required],
    }),
    description: this.formBuilder.nonNullable.control("", {
      validators: [] }),
  });

  impactEdit = signal<Impact | undefined>(undefined);
  loading = signal(false);
  error = signal<string | undefined>(undefined);
  routeId = signal<string>('Nouvel impact sur la priorité');


  isEditMode = signal(false);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) return;

    this.loading.set(true);

    this.impactFormService.load(Number(id)).subscribe({
      next: (impact) => {
        this.isEditMode.set(true);
        this.impactEdit.set(impact);
        this.form.patchValue(this.impactFormService.mapToForm(impact));
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Erreur');
      },
    });
  }

  onSubmit() {
    const dto = this.impactFormService.mapToDto(this.form.getRawValue(), this.impactEdit());

    this.impactFormService.save(dto, this.isEditMode()).subscribe({
      next: () => {
        this.loading.set(false);
        this.router.navigateByUrl('/admin/impact');
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Une erreur est survenue');
      },
    });
  }
}
