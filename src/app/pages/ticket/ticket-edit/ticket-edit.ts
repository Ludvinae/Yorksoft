import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TicketFormService } from '../../../services/form-service/ticket-form-service';
import { ActivatedRoute, Router } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import { FieldSelect } from '../../../components/form/field-select/field-select';
import { FieldText } from '../../../components/form/field-text/field-text';
import { GenericFormPage } from '../../../components/form/generic-form-page/generic-form-page';
import { VersionService } from '../../../services/api/version-service';
import { list } from 'postcss';
import { ClientService } from '../../../services/api/client-service';

@Component({
  selector: 'app-ticket-edit',
  imports: [FieldSelect, FieldText, GenericFormPage],
  templateUrl: './ticket-edit.html',
  styleUrl: './ticket-edit.css',
})
export class TicketEdit implements OnInit {
  formBuilder = inject(FormBuilder);
  ticketFormService = inject(TicketFormService);
  router = inject(Router);
  route = inject(ActivatedRoute);
  versionService = inject(VersionService);
  clientService = inject(ClientService);

  form: FormGroup<TicketForm> = this.formBuilder.nonNullable.group({
    title: this.formBuilder.nonNullable.control('', {
      validators: [Validators.required, Validators.maxLength(63), Validators.minLength(3)],
    }),
    description: this.formBuilder.nonNullable.control('', {
      validators: [Validators.required, Validators.minLength(1)],
    }),
    idUrgency: this.formBuilder.nonNullable.control(0, { validators: [Validators.required] }),
    idImpact: this.formBuilder.nonNullable.control(0, { validators: [Validators.required] }),
    idVersion: this.formBuilder.nonNullable.control(0, { validators: [Validators.required] }),
    idTheme: this.formBuilder.nonNullable.control(0, { validators: [Validators.required] }),
    idSoftware: this.formBuilder.nonNullable.control(0, { validators: [Validators.required] }),
  });

  ticketEdit = signal<Ticket | undefined>(undefined);
  loading = signal(false);
  error = signal<string | undefined>(undefined);
  routeId = signal<string>('Nouveau ticket de support');

  listVersion = toSignal(this.versionService.getAll(), { initialValue: [] });

  isEditMode = signal(false);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) return;

    this.loading.set(true);

    this.ticketFormService.load(Number(id)).subscribe({
      next: (ticket) => {
        this.isEditMode.set(true);
        this.ticketEdit.set(ticket);
        this.form.patchValue(this.ticketFormService.mapToForm(ticket));
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Erreur');
      },
    });
  }

  onSubmit() {
    const dto = this.ticketFormService.mapToDto(this.form.getRawValue(), this.ticketEdit());

    this.ticketFormService.save(dto, this.isEditMode()).subscribe({
      next: () => {
        this.loading.set(false);
        this.router.navigateByUrl('/admin/ticket');
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Une erreur est survenue');
      },
    });
  }

  protected readonly list = list;
}
