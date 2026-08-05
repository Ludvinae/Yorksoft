import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketEdit } from './ticket-edit';
import { provideRouter } from '@angular/router';

describe('TicketEdit', () => {
  let component: TicketEdit;
  let fixture: ComponentFixture<TicketEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketEdit],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(TicketEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
