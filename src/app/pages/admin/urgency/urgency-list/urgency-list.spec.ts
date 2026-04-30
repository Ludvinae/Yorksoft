import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UrgencyList } from './urgency-list';

describe('UrgencyList', () => {
  let component: UrgencyList;
  let fixture: ComponentFixture<UrgencyList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UrgencyList],
    }).compileComponents();

    fixture = TestBed.createComponent(UrgencyList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
