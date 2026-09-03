import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderDesktop } from './header-desktop';
import { provideRouter } from '@angular/router';

describe('HeaderDesktop', () => {
  let component: HeaderDesktop;
  let fixture: ComponentFixture<HeaderDesktop>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderDesktop],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(HeaderDesktop);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
