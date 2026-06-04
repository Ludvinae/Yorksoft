import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { TicketService } from '../../../services/api/ticket-service';
import { TitleBar } from '../../../components/title-bar/title-bar/title-bar';
import { TicketStoreService } from '../../../services/business/ticket-store-service';
import { FooterTicket } from '../../../components/footer-ticket/footer-ticket';
import { DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AssignTicketModal } from '../../../components/ticket/assign-ticket-modal/assign-ticket-modal';
import { BadgePriority } from '../../../components/ticket/badge-priority/badge-priority';
import { BadgeSla } from '../../../components/ticket/badge-sla/badge-sla';
import { StatusLabel } from '../../../pipe/StatusLabel';

@Component({
  selector: 'app-ticket-list',
  imports: [
    TitleBar,
    FooterTicket,
    DatePipe,
    RouterLink,
    AssignTicketModal,
    BadgePriority,
    BadgeSla,
    StatusLabel,
  ],
  templateUrl: './ticket-list.html',
  styleUrl: './ticket-list.css',
})
export class TicketList implements OnInit {
  ticketService = inject(TicketService);
  storeService = inject(TicketStoreService);

  baseRoute = '/ticket/';
  selectedTicket: TicketAssignDto | null = null;

  @ViewChild(AssignTicketModal)
  assignModal!: AssignTicketModal;

  ngOnInit() {
    this.storeService.loadTickets();
  }

  onDelete(id: number) {
    this.ticketService.delete(id).subscribe({
      next: () => {
        alert('Supprimé');
        this.storeService.loadTickets();
      },
      error: () => alert('Erreur'),
    });
  }

  isSorted(field: string): boolean {
    return this.storeService.sortField() === field;
  }

  isAsc(): boolean {
    return this.storeService.sortDirection() === 'asc';
  }

  assign(ticket: TicketListDto): void {
    this.selectedTicket = {
      idTicket: ticket.idTicket,
      currentPriority: ticket.currentPriority,
      overdue: ticket.overdue,
      predictedDeadline: ticket.predictedDeadline,
      idTechnician: ticket.idTechnician,
    };

    queueMicrotask(() => {
      this.assignModal.open();
    });
  }

  onAssigned(): void {
    this.assignModal.close();
    this.storeService.loadTickets();
  }

  isNearDeadline(ticket: TicketListDto): boolean {
    if (!ticket.predictedDeadline) {
      return false;
    }

    const deadline = new Date(ticket.predictedDeadline).getTime();
    const now = Date.now();
    const remainingMs = deadline - now;

    if (ticket.currentPriority > 75) {
      return remainingMs < (1000 * 60 * 60) / 2; // 30mn restantes
    } else if (ticket.currentPriority > 50) {
      return remainingMs < 1000 * 60 * 60 * 2; // 2h restantes
    } else if (ticket.currentPriority > 25) {
      return remainingMs < 1000 * 60 * 60 * 6; // 6h restantes
    } else {
      return remainingMs < 1000 * 60 * 60 * 18; // 18h restantes
    }
  }
}
