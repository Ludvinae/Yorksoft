import { TestBed } from '@angular/core/testing';

import { SoftwareTypeFormService } from './software-type-form-service';

describe('SoftwareTypeFormService', () => {
  let service: SoftwareTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SoftwareTypeFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
