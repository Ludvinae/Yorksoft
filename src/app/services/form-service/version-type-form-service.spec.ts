import { TestBed } from '@angular/core/testing';

import { VersionTypeFormService } from './version-type-form-service';

describe('VersionTypeFormService', () => {
  let service: VersionTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VersionTypeFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
