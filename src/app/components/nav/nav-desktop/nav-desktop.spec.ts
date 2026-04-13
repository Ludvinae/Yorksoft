import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavDesktop } from './nav-desktop';

describe('NavDesktop', () => {
  let component: NavDesktop;
  let fixture: ComponentFixture<NavDesktop>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavDesktop],
    }).compileComponents();

    fixture = TestBed.createComponent(NavDesktop);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
