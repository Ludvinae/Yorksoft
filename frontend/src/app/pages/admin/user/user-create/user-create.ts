import { Component, computed, inject, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import { FieldText } from '../../../../components/form/field-text/field-text';
import { FieldSelect } from '../../../../components/form/field-select/field-select';
import { GenericFormPage } from '../../../../components/form/generic-form-page/generic-form-page';
import { UserFormService } from '../../../../services/form-service/user-form-service';
import { UserRoleEnum } from '../../../../models/enum/UserRoleEnum';

const ROLE_LABELS: Record<UserRoleEnum, string> = {
  [UserRoleEnum.TECHNICIAN]: 'Technicien',
  [UserRoleEnum.MANAGER]: 'Manager',
  [UserRoleEnum.DIRECTOR]: 'Directeur',
};

@Component({
  selector: 'app-user-create',
  imports: [FieldText, FieldSelect, GenericFormPage],
  templateUrl: './user-create.html',
  styleUrl: './user-create.css',
})
export class UserCreate {
  formBuilder = inject(FormBuilder);
  userFormService = inject(UserFormService);
  router = inject(Router);

  loading = signal(false);
  error = signal<string | undefined>(undefined);
  routeId = signal<string>('Nouvel utilisateur');

  roleOptions = Object.values(UserRoleEnum).map((value) => ({
    code: value,
    label: ROLE_LABELS[value],
  }));

  form: FormGroup<UserCreateForm> = this.formBuilder.nonNullable.group({
    role: this.formBuilder.control(null, {
      validators: [Validators.required],
    }),
    firstName: this.formBuilder.nonNullable.control('', {
      validators: [Validators.required, Validators.maxLength(127)],
    }),
    lastName: this.formBuilder.nonNullable.control('', {
      validators: [Validators.required, Validators.maxLength(127)],
    }),
    email: this.formBuilder.nonNullable.control('', {
      validators: [Validators.required, Validators.email, Validators.maxLength(127)],
    }),
    phoneNumber: this.formBuilder.control(null, {
      validators: [Validators.maxLength(31)],
    }),
    password: this.formBuilder.control(null, {
      validators: [Validators.required, Validators.minLength(3), Validators.maxLength(127)],
    }),
    rank: this.formBuilder.control(null),
  });

  private role = toSignal(this.form.controls.role.valueChanges, {
    initialValue: this.form.controls.role.value,
  });

  isTechnician = computed(() => this.role() === UserRoleEnum.TECHNICIAN);

  constructor() {
    // Le rang n'est requis que pour un technicien
    this.form.controls.role.valueChanges.subscribe((role: string) => {
      if (role === UserRoleEnum.TECHNICIAN) {
        this.form.controls.rank.addValidators([Validators.required, Validators.min(1)]);
      } else {
        this.form.controls.rank.clearValidators();
        this.form.controls.rank.setValue(null);
      }
      this.form.controls.rank.updateValueAndValidity();
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading.set(true);

    this.userFormService.save(this.form.getRawValue()).subscribe({
      next: () => {
        this.loading.set(false);
        this.router.navigateByUrl('/admin');
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Une erreur est survenue');
      },
    });
  }
}
