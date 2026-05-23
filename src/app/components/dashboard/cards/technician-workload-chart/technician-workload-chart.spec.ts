import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TechnicianWorkloadChart } from './technician-workload-chart';

describe('TechnicianWorkloadChart', () => {
  let component: TechnicianWorkloadChart;
  let fixture: ComponentFixture<TechnicianWorkloadChart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TechnicianWorkloadChart],
    }).compileComponents();

    fixture = TestBed.createComponent(TechnicianWorkloadChart);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
