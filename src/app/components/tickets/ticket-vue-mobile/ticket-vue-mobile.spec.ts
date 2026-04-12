import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketVueMobile } from './ticket-vue-mobile';

describe('TicketVueMobile', () => {
  let component: TicketVueMobile;
  let fixture: ComponentFixture<TicketVueMobile>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketVueMobile],
    }).compileComponents();

    fixture = TestBed.createComponent(TicketVueMobile);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
