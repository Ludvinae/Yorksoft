import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UrgencyFormService } from '../../../../services/form-service/urgency-form-service';
import { ActivatedRoute, Router } from '@angular/router';
import { FieldText } from '../../../../components/form/field-text/field-text';
import { GenericFormPage } from '../../../../components/form/generic-form-page/generic-form-page';

@Component({
  selector: 'app-urgency-edit',
  imports: [
    FieldText,
    GenericFormPage
  ],
  templateUrl: './urgency-edit.html',
  styleUrl: './urgency-edit.css',
})
export class UrgencyEdit implements OnInit {
  formBuilder = inject(FormBuilder);
  urgencyFormService = inject(UrgencyFormService);
  router = inject(Router);
  route = inject(ActivatedRoute);

  form: FormGroup<UrgencyForm> = this.formBuilder.nonNullable.group({
    designation: this.formBuilder.nonNullable.control('', {
      validators: [Validators.required, Validators.maxLength(63), Validators.minLength(1)],
    }),
    priorityFactor: this.formBuilder.control(null, {
      validators: [Validators.required],
    }),
    description: this.formBuilder.nonNullable.control("", {
      validators: [] }),
  });

  urgencyEdit = signal<Urgency | undefined>(undefined);
  loading = signal(false);
  error = signal<string | undefined>(undefined);
  routeId = signal<string>('Nouvelle urgence sur la priorité');

  isEditMode = signal(false);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) return;

    this.loading.set(true);

    this.urgencyFormService.load(Number(id)).subscribe({
      next: (urgency) => {
        this.isEditMode.set(true);
        this.urgencyEdit.set(urgency);
        this.form.patchValue(this.urgencyFormService.mapToForm(urgency));
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Erreur');
      },
    });
  }

  onSubmit() {
    const dto = this.urgencyFormService.mapToDto(this.form.getRawValue(), this.urgencyEdit());

    this.urgencyFormService.save(dto, this.isEditMode()).subscribe({
      next: () => {
        this.loading.set(false);
        this.router.navigateByUrl('/admin/urgency');
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Une erreur est survenue');
      },
    });
  }
}
