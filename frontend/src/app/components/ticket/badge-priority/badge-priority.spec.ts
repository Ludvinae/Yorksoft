import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BadgePriority } from './badge-priority';
import { provideRouter } from '@angular/router';

describe('BadgePriority', () => {
  let component: BadgePriority;
  let fixture: ComponentFixture<BadgePriority>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BadgePriority],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(BadgePriority);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
