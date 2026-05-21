import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FooterTicket } from './footer-ticket';

describe('FooterTicket', () => {
  let component: FooterTicket;
  let fixture: ComponentFixture<FooterTicket>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FooterTicket],
    }).compileComponents();

    fixture = TestBed.createComponent(FooterTicket);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
