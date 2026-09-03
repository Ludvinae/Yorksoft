import { TestBed } from '@angular/core/testing';

import { TicketFilterState } from './ticket-filter-state';

describe('TicketFilterState', () => {
  let service: TicketFilterState;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TicketFilterState);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
