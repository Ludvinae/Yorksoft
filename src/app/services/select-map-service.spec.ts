import { TestBed } from '@angular/core/testing';

import { SelectMapService } from './select-map-service';

describe('SelectMapService', () => {
  let service: SelectMapService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SelectMapService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
