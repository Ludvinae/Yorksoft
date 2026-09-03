import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImpactDetail } from './impact-detail';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('ImpactDetail', () => {
  let component: ImpactDetail;
  let fixture: ComponentFixture<ImpactDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImpactDetail],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(ImpactDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
