import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketList } from './ticket-list';
import { provideRouter } from '@angular/router';

describe('TicketList', () => {
  let component: TicketList;
  let fixture: ComponentFixture<TicketList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketList],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(TicketList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
