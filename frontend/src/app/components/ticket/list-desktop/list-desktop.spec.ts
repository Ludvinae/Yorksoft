import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListDesktop } from './list-desktop';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('ListDesktop', () => {
  let component: ListDesktop;
  let fixture: ComponentFixture<ListDesktop>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListDesktop],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(ListDesktop);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
