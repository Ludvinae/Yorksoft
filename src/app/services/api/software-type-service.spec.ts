import { TestBed } from '@angular/core/testing';

import { SoftwareTypeService } from './software-type-service';

describe('SoftwareTypeService', () => {
  let service: SoftwareTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SoftwareTypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
