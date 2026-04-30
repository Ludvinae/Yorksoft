import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StatusEdit } from './status-edit';

describe('StatusEdit', () => {
  let component: StatusEdit;
  let fixture: ComponentFixture<StatusEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StatusEdit],
    }).compileComponents();

    fixture = TestBed.createComponent(StatusEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
