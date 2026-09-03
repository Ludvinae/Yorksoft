import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TitleBar } from './title-bar';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('TitleBar', () => {
  let component: TitleBar;
  let fixture: ComponentFixture<TitleBar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TitleBar],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(TitleBar);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
