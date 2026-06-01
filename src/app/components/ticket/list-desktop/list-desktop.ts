import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DatePipe, NgClass } from '@angular/common';
import { TicketService } from '../../../services/api/ticket-service';
import { TicketSearchCriteria } from '../../../models/page/TicketSearchCriteria';
import { StatusEnum } from '../../../models/enum/StatusEnum';
import { StatusLabel } from '../../../pipe/StatusLabel';

@Component({
  selector: 'app-list-desktop',
  imports: [FormsModule, DatePipe, NgClass, StatusLabel],
  templateUrl: './list-desktop.html',
  styleUrl: './list-desktop.css',
})
export class ListDesktop implements OnInit {
  tickets: TicketListDto[] = [];

  filters: TicketSearchCriteria = {
    keyword: '',
    statuses: ['ASSIGNED'],
    priorityGreaterThan: 65,
  };

  loading = false;

  search = '';

  page = 0;

  pageSize = 15;

  totalElements = 0;

  totalPages = 0;

  sortField = 'modificationDate';

  sortDirection: 'asc' | 'desc' = 'desc';

  private searchTimeout: any;

  constructor(private ticketService: TicketService) {}

  ngOnInit(): void {
    this.loadTickets();
  }

  loadTickets(): void {
    this.ticketService
      .getTickets(this.filters, this.page, this.pageSize, `${this.sortField},${this.sortDirection}`)
      .subscribe({
        next: (response) => {
          this.tickets = response.content;

          this.totalPages = response.totalPages;

          this.totalElements = response.totalElements;
        },

        error: (err) => {
          console.error(err);
        },
      });
  }

  setFilter(filter: 'all' | 'assigned' | 'priority'): void {
    this.page = 0;

    this.loadTickets();
  }

  sortBy(field: string): void {
    if (this.sortField === field) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = field;

      this.sortDirection = 'asc';
    }

    this.loadTickets();
  }

  nextPage(): void {
    if (this.page < this.totalPages - 1) {
      this.page++;

      this.loadTickets();
    }
  }

  previousPage(): void {
    if (this.page > 0) {
      this.page--;

      this.loadTickets();
    }
  }

  searchWithDelay(): void {
    clearTimeout(this.searchTimeout);

    this.searchTimeout = setTimeout(() => {
      this.page = 0;

      this.loadTickets();
    }, 300);
  }

}
