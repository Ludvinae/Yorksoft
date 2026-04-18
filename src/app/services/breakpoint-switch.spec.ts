import { TestBed } from '@angular/core/testing';

import { BreakpointSwitch } from './breakpoint-switch';

describe('BreakpointSwitch', () => {
  let service: BreakpointSwitch;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BreakpointSwitch);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
