import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { TicketService } from '../../../services/api/ticket-service';
import { TitleBar } from '../../../components/title-bar/title-bar/title-bar';
import { TicketStoreService } from '../../../services/business/ticket-store-service';
import { FooterTicket } from '../../../components/footer-ticket/footer-ticket';
import { DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AssignTicketModal } from '../../../components/ticket/assign-ticket-modal/assign-ticket-modal';
import { BadgePriority } from '../../../components/ticket/badge-priority/badge-priority';

@Component({
  selector: 'app-ticket-list',
  imports: [TitleBar, FooterTicket, DatePipe, RouterLink, AssignTicketModal, BadgePriority],
  templateUrl: './ticket-list.html',
  styleUrl: './ticket-list.css',
})
export class TicketList implements OnInit {
  ticketService = inject(TicketService);
  storeService = inject(TicketStoreService);

  baseRoute = '/ticket/';
  selectedTicket: TicketListDto | null = null;

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
    this.selectedTicket = ticket;

    queueMicrotask(() => {
      this.assignModal.open();
    });
  }

  onAssigned(): void {
    this.assignModal.close();
  }
}
