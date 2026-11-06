import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TechnicianStatusChart } from './technician-status-chart';

describe('TechnicianStatusChart', () => {
  let component: TechnicianStatusChart;
  let fixture: ComponentFixture<TechnicianStatusChart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TechnicianStatusChart],
    }).compileComponents();

    fixture = TestBed.createComponent(TechnicianStatusChart);
    component = fixture.componentInstance;
  });

  it('orders statuses by workflow and labels them in French with their count', () => {
    fixture.componentRef.setInput('stats', [
      { statusCode: 'WAITING_CLIENT', numberOfTickets: 1, statusColorCode: 4 },
      { statusCode: 'ASSIGNED', numberOfTickets: 3, statusColorCode: 2 },
    ]);

    expect(component.chartData().labels).toEqual(['Assigné (3)', 'Attente client (1)']);
    expect(component.chartData().datasets[0].data).toEqual([3, 1]);
  });

  it('shows an empty state without active tickets', () => {
    fixture.componentRef.setInput('stats', []);
    fixture.detectChanges();

    expect(fixture.nativeElement.textContent).toContain('Aucun ticket actif');
  });
});
