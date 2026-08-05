import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StatusEdit } from './status-edit';
import { provideRouter } from '@angular/router';

describe('StatusEdit', () => {
  let component: StatusEdit;
  let fixture: ComponentFixture<StatusEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StatusEdit],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(StatusEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
