import { Component, inject, OnInit, signal } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FieldText } from '../../../../components/form/field-text/field-text';
import { toSignal } from '@angular/core/rxjs-interop';
import { SoftwareFormService } from '../../../../services/form-service/software-form-service';
import { SoftwareTypeService } from '../../../../services/api/software-type-service';
import { FieldSelect } from '../../../../components/form/field-select/field-select';
import { GenericFormPage } from '../../../../components/form/generic-form-page/generic-form-page';

@Component({
  selector: 'app-software-edit',
  imports: [FormsModule, ReactiveFormsModule, FieldText, FieldSelect, GenericFormPage],
  templateUrl: './software-edit.html',
  styleUrl: './software-edit.css',
})
export class SoftwareEdit implements OnInit {
  formBuilder = inject(FormBuilder);
  softwareFormService = inject(SoftwareFormService);
  router = inject(Router);
  route = inject(ActivatedRoute);
  softwareTypeService = inject(SoftwareTypeService);

  form: FormGroup<SoftwareForm> = this.formBuilder.nonNullable.group({
    name: this.formBuilder.nonNullable.control('', {
      validators: [Validators.required, Validators.maxLength(127), Validators.minLength(1)],
    }),
    description: this.formBuilder.nonNullable.control('', {
      validators: [],
    }),
    idSoftwareType: this.formBuilder.nonNullable.control(0, {
      validators: [],
    }),
  });

  softwareEdit = signal<Software | undefined>(undefined);
  loading = signal(false);
  error = signal<string | undefined>(undefined);
  routeId = signal<string>('Nouveau logiciel');

  listSoftwareType = toSignal(this.softwareTypeService.getAll(), { initialValue: [] });

  isEditMode = signal(false);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) return;

    this.loading.set(true);

    this.softwareFormService.load(Number(id)).subscribe({
      next: (software) => {
        this.isEditMode.set(true);
        this.softwareEdit.set(software);
        this.form.patchValue(this.softwareFormService.mapToForm(software));
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Erreur');
      },
    });
  }

  onSubmit() {
    const dto = this.softwareFormService.mapToDto(this.form.getRawValue(), this.softwareEdit());

    this.softwareFormService.save(dto, this.isEditMode()).subscribe({
      next: () => {
        this.loading.set(false);
        this.router.navigateByUrl('/admin/software');
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Une erreur est survenue');
      },
    });
  }
}
