import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SoftwareTypeDetail } from './software-type-detail';

describe('SoftwareTypeDetail', () => {
  let component: SoftwareTypeDetail;
  let fixture: ComponentFixture<SoftwareTypeDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SoftwareTypeDetail],
    }).compileComponents();

    fixture = TestBed.createComponent(SoftwareTypeDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
