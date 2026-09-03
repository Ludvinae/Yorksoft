import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketStatusPieChart } from './ticket-status-pie-chart';
import { provideRouter } from '@angular/router';

describe('TicketStatusPieChart', () => {
  let component: TicketStatusPieChart;
  let fixture: ComponentFixture<TicketStatusPieChart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketStatusPieChart],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(TicketStatusPieChart);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('stats', []);
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
