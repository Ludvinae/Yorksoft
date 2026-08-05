import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketSoftwareChart } from './ticket-software-chart';
import { provideRouter } from '@angular/router';

describe('TicketSoftwareChart', () => {
  let component: TicketSoftwareChart;
  let fixture: ComponentFixture<TicketSoftwareChart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketSoftwareChart],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(TicketSoftwareChart);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('softwareStats', []);
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
