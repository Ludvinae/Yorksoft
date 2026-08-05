import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UrgencyEdit } from './urgency-edit';
import { provideRouter } from '@angular/router';

describe('UrgencyEdit', () => {
  let component: UrgencyEdit;
  let fixture: ComponentFixture<UrgencyEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UrgencyEdit],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(UrgencyEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
