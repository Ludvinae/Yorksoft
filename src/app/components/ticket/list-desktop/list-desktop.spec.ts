import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListDesktop } from './list-desktop';
import { provideRouter } from '@angular/router';

describe('ListDesktop', () => {
  let component: ListDesktop;
  let fixture: ComponentFixture<ListDesktop>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListDesktop],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(ListDesktop);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
