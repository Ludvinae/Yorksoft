import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericFormPage } from './generic-form-page';

describe('GenericFormPage', () => {
  let component: GenericFormPage;
  let fixture: ComponentFixture<GenericFormPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenericFormPage],
    }).compileComponents();

    fixture = TestBed.createComponent(GenericFormPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
