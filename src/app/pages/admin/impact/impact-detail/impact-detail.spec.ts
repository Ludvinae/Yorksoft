import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImpactDetail } from './impact-detail';

describe('ImpactDetail', () => {
  let component: ImpactDetail;
  let fixture: ComponentFixture<ImpactDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImpactDetail],
    }).compileComponents();

    fixture = TestBed.createComponent(ImpactDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
