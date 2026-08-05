import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Dock } from './dock';
import { provideRouter } from '@angular/router';

describe('Dock', () => {
  let component: Dock;
  let fixture: ComponentFixture<Dock>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Dock],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(Dock);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
