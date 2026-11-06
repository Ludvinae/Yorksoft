import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TechnicianEvolutionChart } from './technician-evolution-chart';

describe('TechnicianEvolutionChart', () => {
  let component: TechnicianEvolutionChart;
  let fixture: ComponentFixture<TechnicianEvolutionChart>;

  const points: TicketEvolutionPoint[] = [
    { date: '2026-11-02', takenInCharge: 2, closed: 0 },
    { date: '2026-11-09', takenInCharge: 0, closed: 1 },
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TechnicianEvolutionChart],
    }).compileComponents();

    fixture = TestBed.createComponent(TechnicianEvolutionChart);
    component = fixture.componentInstance;
  });

  it('builds one series for taken in charge and one for closed', () => {
    fixture.componentRef.setInput('points', points);

    const data = component.chartData();
    expect(data.datasets.map((d) => d.label)).toEqual(['Pris en charge', 'Clos']);
    expect(data.datasets[0].data).toEqual([2, 0]);
    expect(data.datasets[1].data).toEqual([0, 1]);
  });

  it('labels weekly points with the week start', () => {
    fixture.componentRef.setInput('points', points);
    fixture.componentRef.setInput('unit', 'week');

    expect(component.labels()[0]).toMatch(/^sem\. du 2 nov/);
  });

  it('shows an empty state when nothing happened in the period', () => {
    fixture.componentRef.setInput('points', [{ date: '2026-11-02', takenInCharge: 0, closed: 0 }]);
    fixture.detectChanges();

    expect(fixture.nativeElement.textContent).toContain('Aucun ticket pris en charge ou clos');
  });
});
