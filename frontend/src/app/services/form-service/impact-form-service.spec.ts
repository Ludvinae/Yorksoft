import { TestBed } from '@angular/core/testing';

import { ImpactFormService } from './impact-form-service';

describe('ImpactFormService', () => {
  let service: ImpactFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImpactFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
