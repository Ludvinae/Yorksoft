import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableRow } from './table-row';

describe('TableRow', () => {
  let component: TableRow;
  let fixture: ComponentFixture<TableRow>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TableRow],
    }).compileComponents();

    fixture = TestBed.createComponent(TableRow);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
