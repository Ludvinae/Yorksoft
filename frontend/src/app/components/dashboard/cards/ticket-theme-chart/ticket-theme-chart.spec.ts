import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketThemeChart } from './ticket-theme-chart';
import { provideRouter } from '@angular/router';

describe('TicketThemeChart', () => {
  let component: TicketThemeChart;
  let fixture: ComponentFixture<TicketThemeChart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketThemeChart],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(TicketThemeChart);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('themeStats', []);
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
