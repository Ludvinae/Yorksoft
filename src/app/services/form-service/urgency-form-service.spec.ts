import { TestBed } from '@angular/core/testing';

import { UrgencyFormService } from './urgency-form-service';

describe('UrgencyFormService', () => {
  let service: UrgencyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UrgencyFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
