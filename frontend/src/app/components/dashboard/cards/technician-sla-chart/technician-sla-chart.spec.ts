import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TechnicianSlaChart } from './technician-sla-chart';

describe('TechnicianSlaChart', () => {
  let component: TechnicianSlaChart;
  let fixture: ComponentFixture<TechnicianSlaChart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TechnicianSlaChart],
    }).compileComponents();

    fixture = TestBed.createComponent(TechnicianSlaChart);
    component = fixture.componentInstance;
  });

  it('stacks on-time and late tickets and sums them', () => {
    fixture.componentRef.setInput('points', [
      { date: '2026-10-01', onTime: 3, late: 1 },
      { date: '2026-11-01', onTime: 2, late: 0 },
    ]);
    fixture.componentRef.setInput('unit', 'month');

    const data = component.chartData();
    expect(data.datasets.map((d) => d.label)).toEqual(['Dans les délais', 'En retard']);
    expect(data.datasets[0].data).toEqual([3, 2]);
    expect(data.datasets[1].data).toEqual([1, 0]);
    expect(component.totals()).toEqual({ onTime: 5, late: 1, ratio: 5 / 6 });
  });

  it('shows an empty state without closed tickets', () => {
    fixture.componentRef.setInput('points', [{ date: '2026-11-02', onTime: 0, late: 0 }]);
    fixture.detectChanges();

    expect(fixture.nativeElement.textContent).toContain('Aucun ticket clos');
  });
});
