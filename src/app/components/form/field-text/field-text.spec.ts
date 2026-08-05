import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FieldText } from './field-text';
import { provideRouter } from '@angular/router';
import { FormControl } from '@angular/forms';

describe('FieldText', () => {
  let component: FieldText;
  let fixture: ComponentFixture<FieldText>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FieldText],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(FieldText);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('control', new FormControl());
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
