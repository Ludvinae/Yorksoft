import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketSoftwareChart } from './ticket-software-chart';

describe('TicketSoftwareChart', () => {
  let component: TicketSoftwareChart;
  let fixture: ComponentFixture<TicketSoftwareChart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketSoftwareChart],
    }).compileComponents();

    fixture = TestBed.createComponent(TicketSoftwareChart);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
