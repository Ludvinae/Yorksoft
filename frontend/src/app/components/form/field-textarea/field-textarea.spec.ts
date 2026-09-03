import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FieldTextarea } from './field-textarea';
import { provideRouter } from '@angular/router';
import { FormControl } from '@angular/forms';

describe('FieldTextarea', () => {
  let component: FieldTextarea;
  let fixture: ComponentFixture<FieldTextarea>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FieldTextarea],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(FieldTextarea);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('control', new FormControl());
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
