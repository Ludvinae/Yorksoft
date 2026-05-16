import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DatePipe, NgClass } from '@angular/common';
import { TicketService } from '../../../services/api/ticket-service';

@Component({
  selector: 'app-list-desktop',
  imports: [FormsModule, DatePipe, NgClass],
  templateUrl: './list-desktop.html',
  styleUrl: './list-desktop.css',
})
export class ListDesktop implements OnInit {
  tickets: TicketListDto[] = [];

  loading = false;

  search = '';

  filter: 'all' | 'assigned' | 'priority' = 'all';

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
    this.loading = true;

    this.ticketService
      .getTickets(
        {
          search: this.search,

          assigned: this.filter === 'assigned',

          priorityOnly: this.filter === 'priority',
        },

        this.page,

        this.pageSize,

        `${this.sortField},${this.sortDirection}`,
      )
      .subscribe({
        next: (response) => {
          this.tickets = response.content;

          this.totalElements = response.totalElements;

          this.totalPages = response.totalPages;

          this.page = response.number;

          this.loading = false;
        },

        error: (err) => {
          console.error(err);

          this.loading = false;
        },
      });
  }

  setFilter(filter: 'all' | 'assigned' | 'priority'): void {
    this.filter = filter;

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

  onSearch(): void {
    this.page = 0;

    this.loadTickets();
  }

  searchWithDelay(): void {
    clearTimeout(this.searchTimeout);

    this.searchTimeout = setTimeout(() => {
      this.page = 0;

      this.loadTickets();
    }, 300);
  }
}
