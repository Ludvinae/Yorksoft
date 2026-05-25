import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BadgePriority } from './badge-priority';

describe('BadgePriority', () => {
  let component: BadgePriority;
  let fixture: ComponentFixture<BadgePriority>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BadgePriority],
    }).compileComponents();

    fixture = TestBed.createComponent(BadgePriority);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
