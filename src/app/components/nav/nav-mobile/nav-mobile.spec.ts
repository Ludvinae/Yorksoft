import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavMobile } from './nav-mobile';
import { provideRouter } from '@angular/router';

describe('NavMobile', () => {
  let component: NavMobile;
  let fixture: ComponentFixture<NavMobile>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavMobile],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(NavMobile);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
