import { ComponentFixture, TestBed } from '@angular/core/testing';
import { signal } from '@angular/core';
import { Location } from '@angular/common';
import { Navigation, provideRouter, Router } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { vi } from 'vitest';

import { BackButton } from './back-button';
import { AuthService } from '../../../services/business/auth-service';

describe('BackButton', () => {
  let component: BackButton;
  let fixture: ComponentFixture<BackButton>;
  let router: Router;
  let location: Location;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BackButton],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(BackButton);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    location = TestBed.inject(Location);
    await fixture.whenStable();
  });

  function mockLastNavigation(nav: Partial<Navigation> | null): void {
    // Read-only getter (not spyable with vi.spyOn's typings): shadow it on this instance
    Object.defineProperty(router, 'lastSuccessfulNavigation', {
      get: () => signal(nav as Navigation | null),
      configurable: true,
    });
  }

  it('goes back when the user came from another in-app page', () => {
    mockLastNavigation({ previousNavigation: {} as Navigation });
    const back = vi.spyOn(location, 'back').mockImplementation(() => {});

    component.goBack();

    expect(back).toHaveBeenCalled();
  });

  it('falls back to home on a direct load when logged out', () => {
    mockLastNavigation({ previousNavigation: null });
    const navigate = vi.spyOn(router, 'navigateByUrl').mockResolvedValue(true);

    component.goBack();

    expect(navigate).toHaveBeenCalledWith('/');
  });

  it('falls back to the dashboard on a direct load when logged in', () => {
    mockLastNavigation(null);
    TestBed.inject(AuthService).jwtInfo.set({ id: 1 } as JwtInfo);
    const navigate = vi.spyOn(router, 'navigateByUrl').mockResolvedValue(true);

    component.goBack();

    expect(navigate).toHaveBeenCalledWith('/dashboard');
  });
});
