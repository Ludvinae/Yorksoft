import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VersionTypeDetail } from './version-type-detail';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('VersionTypeDetail', () => {
  let component: VersionTypeDetail;
  let fixture: ComponentFixture<VersionTypeDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VersionTypeDetail],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(VersionTypeDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
