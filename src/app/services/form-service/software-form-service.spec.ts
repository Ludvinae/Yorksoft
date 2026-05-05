import { TestBed } from '@angular/core/testing';

import { SoftwareFormService } from './software-form-service';

describe('SoftwareFormService', () => {
  let service: SoftwareFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SoftwareFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
