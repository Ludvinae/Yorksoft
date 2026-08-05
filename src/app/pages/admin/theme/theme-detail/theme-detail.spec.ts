import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThemeDetail } from './theme-detail';
import { provideRouter } from '@angular/router';

describe('ThemeDetail', () => {
  let component: ThemeDetail;
  let fixture: ComponentFixture<ThemeDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ThemeDetail],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(ThemeDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
