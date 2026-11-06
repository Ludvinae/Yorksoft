import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TechnicianAgeChart } from './technician-age-chart';

describe('TechnicianAgeChart', () => {
  let component: TechnicianAgeChart;
  let fixture: ComponentFixture<TechnicianAgeChart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TechnicianAgeChart],
    }).compileComponents();

    fixture = TestBed.createComponent(TechnicianAgeChart);
    component = fixture.componentInstance;
  });

  it('shows the four age bands in order, oldest darkest', () => {
    fixture.componentRef.setInput('counts', [1, 0, 2, 3]);

    const data = component.chartData();
    expect(data.labels).toEqual(['Moins de 1 j', '1 à 3 j', '3 à 7 j', '7 j et plus']);
    expect(data.datasets[0].data).toEqual([1, 0, 2, 3]);
    expect((data.datasets[0].backgroundColor as string[]).length).toBe(4);
  });

  it('shows an empty state without active tickets', () => {
    fixture.componentRef.setInput('counts', [0, 0, 0, 0]);
    fixture.detectChanges();

    expect(fixture.nativeElement.textContent).toContain('Aucun ticket actif');
  });
});
