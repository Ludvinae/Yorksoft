import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DesktopTableRow } from './desktop-table-row';

describe('DesktopTableRow', () => {
  let component: DesktopTableRow;
  let fixture: ComponentFixture<DesktopTableRow>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DesktopTableRow],
    }).compileComponents();

    fixture = TestBed.createComponent(DesktopTableRow);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
