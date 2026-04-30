import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SoftwareTypeEdit } from './software-type-edit';

describe('SoftwareTypeEdit', () => {
  let component: SoftwareTypeEdit;
  let fixture: ComponentFixture<SoftwareTypeEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SoftwareTypeEdit],
    }).compileComponents();

    fixture = TestBed.createComponent(SoftwareTypeEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
