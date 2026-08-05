import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Knowledge } from './knowledge';
import { provideRouter } from '@angular/router';

describe('Knowledge', () => {
  let component: Knowledge;
  let fixture: ComponentFixture<Knowledge>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Knowledge],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(Knowledge);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
