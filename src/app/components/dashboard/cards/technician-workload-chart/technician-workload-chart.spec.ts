import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TechnicianWorkloadChart } from './technician-workload-chart';
import { provideRouter } from '@angular/router';

describe('TechnicianWorkloadChart', () => {
  let component: TechnicianWorkloadChart;
  let fixture: ComponentFixture<TechnicianWorkloadChart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TechnicianWorkloadChart],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(TechnicianWorkloadChart);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('workloadStats', []);
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
