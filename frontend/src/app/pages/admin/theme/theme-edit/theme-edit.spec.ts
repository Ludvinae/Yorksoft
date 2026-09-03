import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThemeEdit } from './theme-edit';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('ThemeEdit', () => {
  let component: ThemeEdit;
  let fixture: ComponentFixture<ThemeEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ThemeEdit],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(ThemeEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
