import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DesktopTableHeader } from './desktop-table-header';

describe('DesktopTableHeader', () => {
  let component: DesktopTableHeader;
  let fixture: ComponentFixture<DesktopTableHeader>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DesktopTableHeader],
    }).compileComponents();

    fixture = TestBed.createComponent(DesktopTableHeader);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
