import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DesktopTable } from './desktop-table';

describe('DesktopTable', () => {
  let component: DesktopTable;
  let fixture: ComponentFixture<DesktopTable>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DesktopTable],
    }).compileComponents();

    fixture = TestBed.createComponent(DesktopTable);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
