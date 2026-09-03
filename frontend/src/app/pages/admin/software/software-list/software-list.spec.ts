import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SoftwareList } from './software-list';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('SoftwareList', () => {
  let component: SoftwareList;
  let fixture: ComponentFixture<SoftwareList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SoftwareList],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(SoftwareList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
