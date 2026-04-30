import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImpactEdit } from './impact-edit';

describe('ImpactEdit', () => {
  let component: ImpactEdit;
  let fixture: ComponentFixture<ImpactEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImpactEdit],
    }).compileComponents();

    fixture = TestBed.createComponent(ImpactEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
