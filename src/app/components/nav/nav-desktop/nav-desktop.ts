import { Component, inject, Input } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { TicketFilterState } from '../../../services/ui/ticket-filter-state';
import { TicketFilterStateEnum } from '../../../models/enum/ticket-filter-state-enum';

@Component({
  selector: 'app-nav-desktop',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './nav-desktop.html',
  styleUrl: './nav-desktop.css',
})
export class NavDesktop {
  @Input() router!: Router;

  filterState = inject(TicketFilterState);

  protected isActive(route: string): boolean {
    return this.router.url.startsWith(route);
  }

  goToTickets(): void {
    console.log('avant filtre');

    this.filterState.setFilter(TicketFilterStateEnum.ALL);

    console.log('avant navigation');

    this.router.navigate(['/ticket']);
  }
}
