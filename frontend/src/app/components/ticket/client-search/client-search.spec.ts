import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientSearch } from './client-search';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('ClientSearch', () => {
  let component: ClientSearch;
  let fixture: ComponentFixture<ClientSearch>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientSearch],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(ClientSearch);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
