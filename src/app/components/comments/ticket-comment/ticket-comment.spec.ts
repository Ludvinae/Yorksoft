import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketComment } from './ticket-comment';
import { provideRouter } from '@angular/router';

describe('TicketComment', () => {
  let component: TicketComment;
  let fixture: ComponentFixture<TicketComment>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketComment],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(TicketComment);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
