import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SoftwareTypeList } from './software-type-list';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('SoftwareTypeList', () => {
  let component: SoftwareTypeList;
  let fixture: ComponentFixture<SoftwareTypeList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SoftwareTypeList],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(SoftwareTypeList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
