import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericFormPage } from './generic-form-page';
import { provideRouter } from '@angular/router';
import { FormGroup } from '@angular/forms';

describe('GenericFormPage', () => {
  let component: GenericFormPage;
  let fixture: ComponentFixture<GenericFormPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenericFormPage],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(GenericFormPage);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('formGroup', new FormGroup({}));
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
