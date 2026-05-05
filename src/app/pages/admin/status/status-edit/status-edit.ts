import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StatusFormService } from '../../../../services/form-service/status-form-service';
import { ActivatedRoute, Router } from '@angular/router';
import { FieldText } from '../../../../components/form/field-text/field-text';
import { GenericFormPage } from '../../../../components/form/generic-form-page/generic-form-page';

@Component({
  selector: 'app-status-edit',
  imports: [FieldText, GenericFormPage],
  templateUrl: './status-edit.html',
  styleUrl: './status-edit.css',
})
export class StatusEdit implements OnInit {
  formBuilder = inject(FormBuilder);
  statusFormService = inject(StatusFormService);
  router = inject(Router);
  route = inject(ActivatedRoute);

  form: FormGroup<StatusForm> = this.formBuilder.nonNullable.group({
    designation: this.formBuilder.nonNullable.control('', {
      validators: [Validators.required, Validators.maxLength(127), Validators.minLength(3)],
    }),
    displayOrder: this.formBuilder.nonNullable.control(0, {
      validators: [],
    }),
  });

  statusEdit = signal<Status | undefined>(undefined);
  loading = signal(false);
  error = signal<string | undefined>(undefined);
  routeId = signal<string>('Nouveau statut de ticket');

  isEditMode = signal(false);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) return;

    this.loading.set(true);

    this.statusFormService.load(Number(id)).subscribe({
      next: (status) => {
        this.isEditMode.set(true);
        this.statusEdit.set(status);
        this.form.patchValue(this.statusFormService.mapToForm(status));
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Erreur');
      },
    });
  }

  onSubmit() {
    const dto = this.statusFormService.mapToDto(this.form.getRawValue(), this.statusEdit());

    this.statusFormService.save(dto, this.isEditMode()).subscribe({
      next: () => {
        this.loading.set(false);
        this.router.navigateByUrl('/admin/status');
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Une erreur est survenue');
      },
    });
  }
}
