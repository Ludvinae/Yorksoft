import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FooterTicket } from './footer-ticket';
import { provideRouter } from '@angular/router';

describe('FooterTicket', () => {
  let component: FooterTicket;
  let fixture: ComponentFixture<FooterTicket>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FooterTicket],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(FooterTicket);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
