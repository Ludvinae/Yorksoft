import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketStatusPieChart } from './ticket-status-pie-chart';

describe('TicketStatusPieChart', () => {
  let component: TicketStatusPieChart;
  let fixture: ComponentFixture<TicketStatusPieChart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketStatusPieChart],
    }).compileComponents();

    fixture = TestBed.createComponent(TicketStatusPieChart);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
