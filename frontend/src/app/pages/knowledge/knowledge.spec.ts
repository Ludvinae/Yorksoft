import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Knowledge } from './knowledge';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('Knowledge', () => {
  let component: Knowledge;
  let fixture: ComponentFixture<Knowledge>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Knowledge],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(Knowledge);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
