import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KpiCard } from './kpi-card';
import { provideRouter } from '@angular/router';

describe('KpiCard', () => {
  let component: KpiCard;
  let fixture: ComponentFixture<KpiCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KpiCard],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(KpiCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
