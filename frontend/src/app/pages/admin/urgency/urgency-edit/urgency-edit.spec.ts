import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UrgencyEdit } from './urgency-edit';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('UrgencyEdit', () => {
  let component: UrgencyEdit;
  let fixture: ComponentFixture<UrgencyEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UrgencyEdit],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(UrgencyEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
