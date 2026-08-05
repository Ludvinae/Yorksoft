import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VersionDetail } from './version-detail';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('VersionDetail', () => {
  let component: VersionDetail;
  let fixture: ComponentFixture<VersionDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VersionDetail],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(VersionDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
