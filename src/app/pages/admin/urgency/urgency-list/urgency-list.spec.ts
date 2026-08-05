import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UrgencyList } from './urgency-list';
import { provideRouter } from '@angular/router';

describe('UrgencyList', () => {
  let component: UrgencyList;
  let fixture: ComponentFixture<UrgencyList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UrgencyList],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(UrgencyList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
