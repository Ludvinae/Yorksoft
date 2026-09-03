import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewTicketForm } from './new-ticket-form';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('NewTicketForm', () => {
  let component: NewTicketForm;
  let fixture: ComponentFixture<NewTicketForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewTicketForm],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(NewTicketForm);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('client', {
      idAppUser: 1,
      firstName: 'Jean',
      lastName: 'Dupont',
      email: 'jean.dupont@test.fr',
      phoneNumber: '0600000000',
      importance: 1,
      softwareIdList: [],
    });
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
