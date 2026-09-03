import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavDesktop } from './nav-desktop';
import { provideRouter, Router } from '@angular/router';

describe('NavDesktop', () => {
  let component: NavDesktop;
  let fixture: ComponentFixture<NavDesktop>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavDesktop],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(NavDesktop);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('router', TestBed.inject(Router));
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
