import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormSet } from './form-set';

describe('FormSet', () => {
  let component: FormSet;
  let fixture: ComponentFixture<FormSet>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormSet],
    }).compileComponents();

    fixture = TestBed.createComponent(FormSet);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
