import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UrgencyDetail } from './urgency-detail';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('UrgencyDetail', () => {
  let component: UrgencyDetail;
  let fixture: ComponentFixture<UrgencyDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UrgencyDetail],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(UrgencyDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
