import { TestBed } from '@angular/core/testing';

import { StatusFormService } from './status-form-service';

describe('StatusFormService', () => {
  let service: StatusFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StatusFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
