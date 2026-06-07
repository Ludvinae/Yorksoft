import { computed, inject, Injectable, signal } from '@angular/core';
import { TicketFilterStateEnum } from '../../models/enum/ticket-filter-state-enum';
import { AuthService } from '../business/auth-service';
import { TicketSearchCriteria } from '../../models/search/TicketSearchCriteria';

@Injectable({
  providedIn: 'root',
})
export class TicketFilterState {
  auth = inject(AuthService);

  readonly filter = signal<TicketFilterStateEnum>(TicketFilterStateEnum.NONE);

  readonly selectedFilter = signal<TicketFilterStateEnum>(TicketFilterStateEnum.ALL);

  readonly criteria = computed<TicketSearchCriteria>(() => {
    switch (this.selectedFilter()) {
      case TicketFilterStateEnum.OPEN:
        return {
          statuses: ['OPEN'],
        };

      case TicketFilterStateEnum.ASSIGNED:
        return {
          assignedTo: this.auth.jwtInfo()?.id,
        };

      case TicketFilterStateEnum.PRIORITY:
        return {
          priorityGreaterThan: 65,
        };

      case TicketFilterStateEnum.ALL:
      default:
        return {};
    }
  });

  setFilter(filter: TicketFilterStateEnum): void {
    console.log('SET FILTER', filter);
    this.selectedFilter.set(filter);

  }


}
