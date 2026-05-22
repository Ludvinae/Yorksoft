import { Component, inject, Input, OnInit } from '@angular/core';
import { FieldSelect } from '../../form/field-select/field-select';
import { FieldText } from '../../form/field-text/field-text';
import { FieldTextarea } from '../../form/field-textarea/field-textarea';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { VersionService } from '../../../services/api/version-service';
import { toSignal } from '@angular/core/rxjs-interop';
import { SoftwareService } from '../../../services/api/software-service';
import { ImpactService } from '../../../services/api/impact-service';
import { UrgencyService } from '../../../services/api/urgency-service';
import { ThemeService } from '../../../services/api/theme-service';

@Component({
  selector: 'app-new-ticket-form',
  imports: [FieldSelect, FieldText, FieldTextarea, ReactiveFormsModule],
  templateUrl: './new-ticket-form.html',
  styleUrl: './new-ticket-form.css',
})
export class NewTicketForm implements OnInit {
  @Input({ required: true })
  client!: ClientListDto;

  formBuilder = inject(FormBuilder);
  router = inject(Router);
  route = inject(ActivatedRoute);

  versionService = inject(VersionService);
  softwareService = inject(SoftwareService);
  impactService = inject(ImpactService);
  urgencyService = inject(UrgencyService);
  themeService = inject(ThemeService);

  ticketForm: FormGroup<TicketForm> = this.formBuilder.nonNullable.group({
    idSoftware: [null, Validators.required],
    idVersion: [{ value: null, disabled: true }, Validators.required],

    idImpact: [null, Validators.required],
    idUrgency: [null, Validators.required],
    idTheme: [null, Validators.required],

    title: ['', [Validators.required, Validators.maxLength(63), Validators.minLength(3)]],
    description: ['', [Validators.required, Validators.minLength(20)]],
  });

  listSoftware = toSignal(this.softwareService.getAll(), { initialValue: [] });
  listImpact = toSignal(this.impactService.getAll(), { initialValue: [] });
  listUrgency = toSignal(this.urgencyService.getAll(), { initialValue: [] });
  listTheme = toSignal(this.themeService.getAll(), { initialValue: [] });

  ngOnInit(): void {
    this.ticketForm.get('idSoftware')?.valueChanges.subscribe((idSoftware) => {
      this.onSoftwareChange(idSoftware as number | null);
    });
  }

  protected isSubmitting() {
    return undefined;
  }

  listVersion: Version[] = [];

  loadVersions(softwareId: number): void {
    this.versionService.getAllBySoftware(softwareId).subscribe((versions) => {
      this.listVersion = versions;
    });
  }

  onSoftwareChange(idSoftware: number | null): void {
    const versionControl = this.ticketForm.get('idVersion');

    // reset version sélectionnée
    versionControl?.reset();

    if (!idSoftware) {
      versionControl?.disable();

      this.listVersion = [];

      return;
    }

    // charger versions du logiciel
    this.loadVersions(idSoftware);

    // réactiver le champ
    setTimeout(() => {
      versionControl?.enable();
    });
  }
}
