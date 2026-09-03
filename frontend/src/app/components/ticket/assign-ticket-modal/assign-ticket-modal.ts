import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  EventEmitter,
  inject,
  Input,
  OnChanges,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { TicketService } from '../../../services/api/ticket-service';
import { TechnicianService } from '../../../services/api/technician-service';
import { AuthService } from '../../../services/business/auth-service';
import { BadgePriority } from '../badge-priority/badge-priority';
import { BadgeSla } from '../badge-sla/badge-sla';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-assign-ticket-modal',
  imports: [BadgePriority, BadgeSla, FormsModule],
  templateUrl: './assign-ticket-modal.html',
  styleUrl: './assign-ticket-modal.css',
})
export class AssignTicketModal implements OnInit {
  @Input()
  ticket!: TicketAssignDto | null;

  @Output()
  assigned = new EventEmitter<void>();

  ticketService = inject(TicketService);
  technicianService = inject(TechnicianService);
  authService = inject(AuthService);
  cdr = inject(ChangeDetectorRef);

  @ViewChild('assignModal')
  modal!: ElementRef<HTMLDialogElement>;

  technicians: TechnicianWorkloadDetailedDto[] = [];
  loading = false;
  reassignmentReason = '';

  ngOnInit(): void {
    if (this.authService.jwtInfo()?.role === 'MANAGER') {
      this.loadTechnicians();
    }
  }

  open(): void {
    this.modal.nativeElement.showModal();
  }

  close(): void {
    this.modal.nativeElement.close();
  }

  loadTechnicians(): void {
    this.loading = true;

    this.technicianService.getAllWorkload().subscribe({
      next: (techs) => {
        this.technicians = techs;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
        this.cdr.detectChanges();
      },
    });
  }

  assignTo(technician: TechnicianWorkloadDetailedDto): void {
    if (!this.ticket) {
      return;
    }
    if (!this.authService.jwtInfo()) {
      return;
    }
    const idManager: number = this.authService.jwtInfo()!.id;

    this.ticketService
      .assign(this.ticket.idTicket, technician.idTechnician, this.reassignmentReason)
      .subscribe({
        next: () => {
          this.loadTechnicians();
          this.assigned.emit();
          this.close();
        },
      });
  }

  get currentTechnician(): TechnicianWorkloadDetailedDto | undefined {
    return this.technicians.find((tech) => tech.idTechnician === this.ticket?.idTechnician);
  }
}
