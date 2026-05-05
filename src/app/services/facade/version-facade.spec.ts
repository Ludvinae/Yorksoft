import { TestBed } from '@angular/core/testing';

import { VersionFormService } from './versionFormService';

describe('VersionFacade', () => {
  let service: VersionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VersionFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
