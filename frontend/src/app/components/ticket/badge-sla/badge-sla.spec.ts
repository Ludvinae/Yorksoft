import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BadgeSla } from './badge-sla';
import { provideRouter } from '@angular/router';

describe('BadgeSla', () => {
  let component: BadgeSla;
  let fixture: ComponentFixture<BadgeSla>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BadgeSla],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(BadgeSla);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
