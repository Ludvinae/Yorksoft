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
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ticket-detail',
  imports: [RouterLink, DatePipe, TicketComment, AssignTicketModal, FormsModule],
  templateUrl: './ticket-detail.html',
  styleUrl: './ticket-detail.css',
})
export class TicketDetail implements OnInit {
  route = inject(ActivatedRoute);
  ticketService = inject(TicketService);
  routeBuilder = inject(RouteBuilderService);
  auth = inject(AuthService);

  ticket = signal<TicketDetailFullDto | null>(null);
  comments = signal<TicketDetailComment[]>([]);

  detail = computed(() => this.ticket()?.details);
  knowledge = computed(() => this.ticket()?.knowledge);
  articles = computed(() => this.ticket()?.articles ?? []);
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
        request$ = this.ticketService.assign(
          this.id()!,
          payload.idAppUser,
          this.auth.jwtInfo()!.id,
          payload.reason,
        );
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

    this.ticketService.updateDescription(ticketId, this.editedDescription).subscribe({
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

    return this.detail()?.currentTechnicianId == idViewer
          || this.auth.jwtInfo()?.role === "ADMIN"
          || this.auth.jwtInfo()?.role === "MANAGER"
  };
}
