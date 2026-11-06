import { Component, ElementRef, EventEmitter, inject, Input, OnInit, Output, signal, ViewChild } from '@angular/core';
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
import { TicketService } from '../../../services/api/ticket-service';
import { AuthService } from '../../../services/business/auth-service';

@Component({
  selector: 'app-new-ticket-form',
  imports: [FieldSelect, FieldText, FieldTextarea, ReactiveFormsModule],
  templateUrl: './new-ticket-form.html',
  styleUrl: './new-ticket-form.css',
})
export class NewTicketForm implements OnInit {
  @Input({ required: true })
  client!: ClientListDto;

  @Output()
  createdTicket = new EventEmitter<Ticket>();

  formBuilder = inject(FormBuilder);
  router = inject(Router);
  route = inject(ActivatedRoute);
  auth = inject(AuthService);

  ticketService = inject(TicketService);
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

  themeForm: FormGroup<ThemeForm> = this.formBuilder.group({
    designation: ["", Validators.required],
    code: ["", Validators.required],
    description: ["", ],
  })

  listSoftware = toSignal(this.softwareService.getAll(), { initialValue: [] });
  listImpact = toSignal(this.impactService.getAll(), { initialValue: [] });
  listUrgency = toSignal(this.urgencyService.getAll(), { initialValue: [] });
  // Read from the service's signal (not a one-shot toSignal) so a theme created from the modal shows up
  listTheme = this.themeService.themeList;

  @ViewChild('themeModal') themeModal!: ElementRef<HTMLDialogElement>;
  themeErrorMessage = signal<string | null>(null);
  isCreatingTheme = signal(false);

  get canCreateTheme(): boolean {
    const role = this.auth.jwtInfo()?.role;
    return role === 'MANAGER' || role === 'ADMIN';
  }

  errorMessage = signal<string | null>(null);
  ticketCreated = signal(false);
  createdTicketId = signal<number | null>(null);
  isSubmitting = signal(false);

  listVersion = signal<VersionSelectDto[]>([]);

  ngOnInit(): void {
    this.themeService.getAll().subscribe();

    this.ticketForm.get('idSoftware')?.valueChanges.subscribe((idSoftware) => {
      this.onSoftwareChange(idSoftware as number | null);
    });
  }

  onSubmit(): void {
    if (this.ticketForm.invalid) {
      this.ticketForm.markAllAsTouched();

      return;
    }

    if (this.isSubmitting()) {
      return;
    }

    this.errorMessage.set(null);

    this.isSubmitting.set(true);

    const payload: TicketCreation = {
      ...(this.ticketForm.getRawValue() as Omit<TicketCreation, 'idClient'>),

      idClient: this.client.idAppUser
    };

    this.ticketService.create(payload).subscribe({
      next: (ticket) => {
        this.ticketCreated.set(true);

        this.createdTicketId.set(ticket.idTicket);
        this.createdTicket.emit(ticket);
      },

      error: (err) => {
        console.error(err);

        this.handleApiError(err);

        this.isSubmitting.set(false);
      },

      complete: () => {
        this.isSubmitting.set(false);
      },
    });
  }

  handleApiError(err: any): void {
    switch (err.status) {
      case 400:
        this.errorMessage.set('Le formulaire contient des données invalides.');
        break;

      case 403:
        this.errorMessage.set('Vous n’avez pas les droits nécessaires.');
        break;

      case 404:
        this.errorMessage.set('Une ressource demandée est introuvable.');
        break;

      case 409:
        this.errorMessage.set('Un ticket similaire existe déjà.');
        break;

      case 429: {
        // Trial accounts are limited to a number of tickets per hour
        const retryAfter = Number(err.headers?.get('Retry-After'));
        const wait = retryAfter ? ` Réessayez dans ${Math.ceil(retryAfter / 60)} min.` : '';
        this.errorMessage.set(`Limite de tickets atteinte pour un compte d'essai.${wait}`);
        break;
      }

      case 500:
        this.errorMessage.set('Une erreur serveur est survenue.');
        break;

      default:
        this.errorMessage.set('Une erreur inattendue est survenue.');
    }
  }

  loadVersions(softwareId: number): void {
    this.versionService.getAllBySoftware(softwareId).subscribe((versions) => {
      this.listVersion.set(versions)
    });
  }

  onSoftwareChange(idSoftware: number | null): void {
    const versionControl = this.ticketForm.get('idVersion');

    // reset version sélectionnée
    versionControl?.reset();

    if (!idSoftware) {
      versionControl?.disable();

      this.listVersion.set([]);

      return;
    }

    // charger versions du logiciel
    this.loadVersions(idSoftware);

    // réactiver le champ
    setTimeout(() => {
      versionControl?.enable();
    });
  }

  openCreateThemeModal(): void {
    this.themeForm.reset({ designation: '', code: '', description: '' });
    this.themeErrorMessage.set(null);
    this.themeModal.nativeElement.showModal();
  }

  createTheme(): void {
    if (this.themeForm.invalid || this.isCreatingTheme()) return;

    this.isCreatingTheme.set(true);
    this.themeErrorMessage.set(null);

    const { designation, code, description } = this.themeForm.getRawValue() as {
      designation: string;
      code: string;
      description: string;
    };
    const theme: Theme = { idTheme: 0, designation, code, description: description || null };

    this.themeService.create(theme).subscribe({
      next: (created) => {
        this.isCreatingTheme.set(false);
        this.themeModal.nativeElement.close();
        // Select the new theme in the ticket form
        this.ticketForm.controls.idTheme.setValue(created.idTheme);
      },
      error: (err) => {
        console.error(err);
        this.isCreatingTheme.set(false);
        this.themeErrorMessage.set(
          err.status === 403
            ? 'Vous n’avez pas les droits nécessaires.'
            : 'La création de la thématique a échoué.',
        );
      },
    });
  }
}
