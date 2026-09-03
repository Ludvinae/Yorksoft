import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VersionList } from './version-list';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('VersionList', () => {
  let component: VersionList;
  let fixture: ComponentFixture<VersionList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VersionList],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(VersionList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
