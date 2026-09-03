import { TestBed } from '@angular/core/testing';

import { VersionTypeService } from './version-type-service';

describe('VersionTypeService', () => {
  let service: VersionTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VersionTypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
