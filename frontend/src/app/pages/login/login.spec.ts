import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Login } from './login';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { environment } from '../../../environments/environment';

describe('Login', () => {
  let component: Login;
  let fixture: ComponentFixture<Login>;
  let httpTesting: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Login],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    httpTesting = TestBed.inject(HttpTestingController);
    fixture = TestBed.createComponent(Login);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  function demoButton(): HTMLButtonElement | undefined {
    const buttons = Array.from(fixture.nativeElement.querySelectorAll('button')) as HTMLButtonElement[];
    return buttons.find((b) => b.textContent?.includes('Essayer'));
  }

  it('should create', () => {
    httpTesting.expectOne(`${environment.serverUrl}/demo`).flush({ enabled: false, ttlHours: 24 });
    expect(component).toBeTruthy();
  });

  it('shows the trial button when the demo is enabled', () => {
    httpTesting.expectOne(`${environment.serverUrl}/demo`).flush({ enabled: true, ttlHours: 24 });
    fixture.detectChanges();

    expect(demoButton()).toBeTruthy();
  });

  it('hides the trial button when the demo is disabled or unreachable', () => {
    httpTesting
      .expectOne(`${environment.serverUrl}/demo`)
      .flush('not found', { status: 404, statusText: 'Not Found' });
    fixture.detectChanges();

    expect(demoButton()).toBeUndefined();
  });
});
