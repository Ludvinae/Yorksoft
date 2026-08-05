import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketNew } from './ticket-new';
import { provideRouter } from '@angular/router';

describe('TicketNew', () => {
  let component: TicketNew;
  let fixture: ComponentFixture<TicketNew>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketNew],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(TicketNew);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
