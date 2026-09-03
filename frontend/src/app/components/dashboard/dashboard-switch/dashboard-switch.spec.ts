import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardSwitch } from './dashboard-switch';

describe('DashboardSwitch', () => {
  let component: DashboardSwitch;
  let fixture: ComponentFixture<DashboardSwitch>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardSwitch],
    }).compileComponents();

    fixture = TestBed.createComponent(DashboardSwitch);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
