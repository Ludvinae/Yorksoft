import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignTicketModal } from './assign-ticket-modal';
import { provideRouter } from '@angular/router';

describe('AssignTicketModal', () => {
  let component: AssignTicketModal;
  let fixture: ComponentFixture<AssignTicketModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AssignTicketModal],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(AssignTicketModal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
