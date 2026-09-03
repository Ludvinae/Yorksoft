import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FieldSelect } from './field-select';
import { provideRouter } from '@angular/router';
import { FormControl } from '@angular/forms';

describe('FieldSelect', () => {
  let component: FieldSelect;
  let fixture: ComponentFixture<FieldSelect>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FieldSelect],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(FieldSelect);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('control', new FormControl());
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
