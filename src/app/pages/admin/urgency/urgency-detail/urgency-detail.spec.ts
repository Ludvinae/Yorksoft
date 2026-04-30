import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UrgencyDetail } from './urgency-detail';

describe('UrgencyDetail', () => {
  let component: UrgencyDetail;
  let fixture: ComponentFixture<UrgencyDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UrgencyDetail],
    }).compileComponents();

    fixture = TestBed.createComponent(UrgencyDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
