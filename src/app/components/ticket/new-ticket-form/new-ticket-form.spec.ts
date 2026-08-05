import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewTicketForm } from './new-ticket-form';
import { provideRouter } from '@angular/router';

describe('NewTicketForm', () => {
  let component: NewTicketForm;
  let fixture: ComponentFixture<NewTicketForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewTicketForm],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(NewTicketForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
