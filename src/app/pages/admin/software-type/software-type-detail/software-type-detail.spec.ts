import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SoftwareTypeDetail } from './software-type-detail';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('SoftwareTypeDetail', () => {
  let component: SoftwareTypeDetail;
  let fixture: ComponentFixture<SoftwareTypeDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SoftwareTypeDetail],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(SoftwareTypeDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
