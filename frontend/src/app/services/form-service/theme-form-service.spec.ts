import { TestBed } from '@angular/core/testing';

import { ThemeFormService } from './theme-form-service';

describe('ThemeFormService', () => {
  let service: ThemeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ThemeFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
