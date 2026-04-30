import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SoftwareEdit } from './software-edit';

describe('SoftwareEdit', () => {
  let component: SoftwareEdit;
  let fixture: ComponentFixture<SoftwareEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SoftwareEdit],
    }).compileComponents();

    fixture = TestBed.createComponent(SoftwareEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
