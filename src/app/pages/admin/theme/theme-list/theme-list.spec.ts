import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThemeList } from './theme-list';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('ThemeList', () => {
  let component: ThemeList;
  let fixture: ComponentFixture<ThemeList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ThemeList],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(ThemeList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
