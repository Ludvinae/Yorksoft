import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketThemeChart } from './ticket-theme-chart';

describe('TicketThemeChart', () => {
  let component: TicketThemeChart;
  let fixture: ComponentFixture<TicketThemeChart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketThemeChart],
    }).compileComponents();

    fixture = TestBed.createComponent(TicketThemeChart);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
