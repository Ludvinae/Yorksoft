import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DesktopTable } from './desktop-table';
import { provideRouter } from '@angular/router';

describe('DesktopTable', () => {
  let component: DesktopTable<any>;
  let fixture: ComponentFixture<DesktopTable<any>>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DesktopTable],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(DesktopTable);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
