import { Component, computed, ElementRef, inject, OnInit, signal, ViewChild } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { TicketService } from '../../../services/api/ticket-service';
import { RouteBuilderService } from '../../../services/business/route-builder-service';
import { tap } from 'rxjs';
import { DatePipe } from '@angular/common';
import { TicketComment } from '../../../components/comments/ticket-comment/ticket-comment';
import { StatusEnum } from '../../../models/enum/StatusEnum';
import { AuthService } from '../../../services/business/auth-service';
import {AssignTicketModal} from '../../../components/ticket/assign-ticket-modal/assign-ticket-modal';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { KnowledgeSelector } from '../../../components/knowledge-selector/knowledge-selector';
import {KnowledgeService} from '../../../services/api/knowledge-service';
import {KnowledgeCreateModal} from '../../../components/knowledge-create-modal/knowledge-create-modal';
import {BadgePriority} from '../../../components/ticket/badge-priority/badge-priority';
import { ClientService } from '../../../services/api/client-service';
import { PageHeader } from '../../../components/layout/page-header/page-header';

@Component({
  selector: 'app-ticket-detail',
  imports: [
    RouterLink,
    DatePipe,
    TicketComment,
    AssignTicketModal,
    FormsModule,
    KnowledgeSelector,
    KnowledgeCreateModal,
    BadgePriority,
    ReactiveFormsModule,
    PageHeader,
  ],
  templateUrl: './ticket-detail.html',
  styleUrl: './ticket-detail.css',
})
export class TicketDetail implements OnInit {
  route = inject(ActivatedRoute);
  ticketService = inject(TicketService);
  routeBuilder = inject(RouteBuilderService);
  auth = inject(AuthService);
  fb = inject(FormBuilder);
  clientService = inject (ClientService);

  ticket = signal<TicketDetailFullDto | null>(null);
  comments = signal<TicketDetailComment[]>([]);
  protected showKnowledgeModal = signal(false);

  detail = computed(() => this.ticket()?.details);
  knowledge = computed(() => this.ticket()?.knowledge);
  possibleTransitions = computed(() => this.ticket()?.possibleStatusTransitions ?? []);

  loading = signal(true);
  error = signal<string | undefined>(undefined);
  id = signal<number | undefined>(undefined);

  label = computed(() => 'ticket #' + this.id());

  selectedAction = signal<StatusEnum | null>(null);
  justification = signal('');

  ticketAssign = computed((): TicketAssignDto | null => {
    if (this.ticket() != null) {
      return {
        idTicket: this.ticket()!.details.idTicket,
        currentPriority: this.ticket()!.details.currentPriority,
        overdue: this.ticket()!.details.overdue,
        predictedDeadline: this.ticket()!.details.predictedDeadline,
        idTechnician: this.ticket()!.details.currentTechnicianId,
      };
    } else return null;
  });

  updateRoute: string | undefined = undefined;
  editingDescription = false;
  editedDescription = '';
  editingSolution = false;
  editedSolution = '';

  @ViewChild(AssignTicketModal)
  assignModal!: AssignTicketModal;

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.id.set(id);

    this.updateRoute = this.routeBuilder.getUpdateRoute(id, 'ticket');

    this.loadTicket();
  }

  private loadTicket(): void {
    const id = this.id();

    if (!id) {
      return;
    }

    this.loading.set(true);

    this.ticketService
      .getDetail(id)
      .pipe(
        tap((ticket) => {
          this.ticket.set(ticket);
          this.comments.set(ticket.comments);
        }),
      )
      .subscribe({
        next: () => {
          this.loading.set(false);
          this.fillClientForm();
        },
        error: () => {
          this.loading.set(false);
          this.error.set('Impossible de charger les détails.');
        },
      });
  }

  protected onCommentCreated(comment: TicketDetailComment): void {
    //this.comments.update((comments) => [comment, ...comments]);
    this.loadTicket();
  }

  protected onCommentUpdated(updatedComment: TicketDetailComment): void {
    this.loadTicket();
    /*
    this.comments.update((comments) =>
      comments.map((comment) =>
        comment.idComment === updatedComment.idComment ? updatedComment : comment,
      ),
    );
     */
  }

  protected readonly StatusEnum = StatusEnum;

  @ViewChild('actionModal')
  actionModal!: ElementRef<HTMLDialogElement>;

  openActionModal(action: StatusEnum): void {
    if (action === StatusEnum.ASSIGNED) {
      queueMicrotask(() => {
        this.assignModal.open();
      });
      return;
    }

    this.selectedAction.set(action);
    this.justification.set('');
    this.actionModal.nativeElement.showModal();
  }

  confirmAction(modal: HTMLDialogElement): void {
    const action = this.selectedAction();

    if (!action || !this.id()) {
      return;
    }

    const payload: TicketTransition = {
      idAppUser: this.auth.jwtInfo() ? this.auth.jwtInfo()!.id : 0,
      reason: this.justification(),
    };

    let request$;

    switch (action) {
      case StatusEnum.IN_PROGRESS:
        if (this.detail()?.currentStatus === StatusEnum.ASSIGNED) {
          request$ = this.ticketService.progressStart(this.id()!, payload);
          break;
        } else {
          request$ = this.ticketService.progressResume(this.id()!, payload);
          break;
        }
      case StatusEnum.WAITING_CLIENT:
        request$ = this.ticketService.waitClient(this.id()!, payload);
        break;

      case StatusEnum.WAITING_THIRD_PARTY:
        request$ = this.ticketService.waitThirdParty(this.id()!, payload);
        break;

      case StatusEnum.SOLVED:
        request$ = this.ticketService.solve(this.id()!, payload);
        break;

      case StatusEnum.CLOSED:
        request$ = this.ticketService.close(this.id()!, payload);
        break;

      case StatusEnum.ASSIGNED:
        request$ = this.ticketService.assign(this.id()!, payload.idAppUser, payload.reason);
        break;

      default:
        return;
    }

    request$.subscribe({
      next: () => {
        modal.close();

        this.loadTicket();
      },
    });
  }

  onAssigned(): void {
    this.loadTicket();
  }

  startDescriptionEdit(): void {
    this.editedDescription = this.detail()?.description ?? '';
    this.editingDescription = true;
  }

  cancelDescriptionEdit(): void {
    this.editingDescription = false;
    this.editedDescription = '';
  }

  saveDescription(): void {
    const ticketId = this.detail()?.idTicket;

    if (!ticketId) {
      return;
    }

    this.ticketService.patch(ticketId, this.editedDescription, '').subscribe({
      next: (updatedTicket) => {
        this.ticket.set(updatedTicket);
        this.editingDescription = false;
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  canEditDescription() {
    const idViewer = this.auth.jwtInfo() ? this.auth.jwtInfo()!.id : 0;

    return (
      this.detail()?.currentTechnicianId == idViewer ||
      this.auth.jwtInfo()?.role === 'ADMIN' ||
      this.auth.jwtInfo()?.role === 'MANAGER'
    );
  }

  startSolutionEdit(): void {
    this.editedSolution = this.detail()?.solution ?? '';
    this.editingSolution = true;
  }

  cancelSolutionEdit(): void {
    this.editingSolution = false;
    this.editedSolution = '';
  }

  saveSolution(): void {
    const ticketId = this.detail()?.idTicket;

    if (!ticketId) {
      return;
    }

    this.ticketService.patch(ticketId, '', this.editedSolution).subscribe({
      next: (updatedTicket) => {
        this.ticket.set(updatedTicket);
        this.editingSolution = false;
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  canEditSolution() {
    const idViewer = this.auth.jwtInfo() ? this.auth.jwtInfo()!.id : 0;

    return (
      this.detail()?.currentTechnicianId == idViewer ||
      this.auth.jwtInfo()?.role === 'ADMIN' ||
      this.auth.jwtInfo()?.role === 'MANAGER'
    );
  }

  protected selectedKnowledge?: KnowledgeSelect;
  protected editingKnowledge = signal(false);
  protected knowledgeError = signal<string | undefined>(undefined);

  // Same rights as the backend: assigned technician, manager or admin, on a ticket not yet closed
  canEditKnowledge(): boolean {
    return this.canEditSolution() && this.detail()?.currentStatus !== StatusEnum.CLOSED;
  }

  protected startKnowledgeEdit(): void {
    this.selectedKnowledge = undefined;
    this.knowledgeError.set(undefined);
    this.editingKnowledge.set(true);
  }

  protected cancelKnowledgeEdit(): void {
    this.knowledgeError.set(undefined);
    this.editingKnowledge.set(false);
  }

  protected onKnowledgeSelected(knowledge: KnowledgeSelect): void {
    const ticketId = this.detail()?.idTicket;

    if (!ticketId) {
      return;
    }

    this.selectedKnowledge = knowledge;
    this.knowledgeError.set(undefined);

    this.ticketService.linkKnowledge(ticketId, knowledge.idKnowledge).subscribe({
      next: (updatedTicket) => {
        this.ticket.set(updatedTicket);
        this.editingKnowledge.set(false);
        this.selectedKnowledge = undefined;
      },
      error: () => {
        this.knowledgeError.set("Impossible d'associer cette connaissance au ticket.");
      },
    });
  }

  protected openKnowledgeModal(): void {
    this.showKnowledgeModal.set(true);
  }

  protected closeKnowledgeModal(): void {
    this.showKnowledgeModal.set(false);
  }

  protected onKnowledgeCreated(knowledge: KnowledgeSelect): void {
    this.showKnowledgeModal.set(false);

    this.onKnowledgeSelected(knowledge);
  }

  protected readonly console = console;

  isEditingClient = false;
  clientForm = this.fb.group({
    id: [0],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    phone: [''],
    importance: [0],
  });

  private fillClientForm(): void {
    const detail = this.detail();

    if (!detail) {
      return;
    }

    this.clientForm.patchValue({
      id: this.detail()?.idClient,
      firstName: this.detail()?.clientFirstName,
      lastName: this.detail()?.clientLastName,
      email: this.detail()?.clientEmail,
      phone: this.detail()?.clientPhone,
      importance: this.detail()?.importance,
    });
  }

  startClientEdit(): void {
    this.resetClientForm();
    this.isEditingClient = true;
  }

  cancelClientEdit(): void {
    this.resetClientForm();
    this.isEditingClient = false;
  }

  private resetClientForm(): void {
    const detail = this.detail();

    if (!detail) {
      return;
    }

    this.clientForm.patchValue({
      id: this.detail()?.idClient,
      firstName: this.detail()?.clientFirstName,
      lastName: this.detail()?.clientLastName,
      email: this.detail()?.clientEmail,
      phone: this.detail()?.clientPhone,
      importance: this.detail()?.importance,
    });
  }

  saveClientEdit(): void {
    if (this.clientForm.invalid) {
      this.clientForm.markAllAsTouched();
      return;
    }

    const formValue = this.clientForm.getRawValue();

    const request: Client = {
      idAppUser: formValue.id ? formValue.id : 0,
      firstName: formValue.firstName,
      lastName: formValue.lastName,
      email: formValue.email ? formValue.email : '',
      phoneNumber: formValue.phone,
      importance: formValue.importance ? formValue.importance : 0,
    };

    this.clientService.update(this.detail()!.idClient, request).subscribe({
      next: () => {
        this.isEditingClient = false;
        this.loadTicket();
      },
    });
  }

  startDetailsEdit() {}
}
