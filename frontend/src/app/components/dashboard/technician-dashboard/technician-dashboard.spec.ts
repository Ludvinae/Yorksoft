import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TechnicianDashboard } from './technician-dashboard';

describe('TechnicianDashboard', () => {
  let component: TechnicianDashboard;
  let fixture: ComponentFixture<TechnicianDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TechnicianDashboard],
    }).compileComponents();

    fixture = TestBed.createComponent(TechnicianDashboard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('formats durations in minutes, hours or days', () => {
    expect(component.formatDuration(null)).toBeNull();
    expect(component.formatDuration(1140)).toBe('19 min');
    expect(component.formatDuration(9000)).toBe('2,5 h');
    expect(component.formatDuration(3 * 86400)).toBe('3 j');
  });
});
