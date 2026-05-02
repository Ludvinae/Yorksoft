import { TestBed } from '@angular/core/testing';

import { RouteBuilderService } from './route-builder-service';

describe('RouteBuilderService', () => {
  let service: RouteBuilderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RouteBuilderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
