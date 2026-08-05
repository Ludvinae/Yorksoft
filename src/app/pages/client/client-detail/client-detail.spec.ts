import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientDetail } from './client-detail';
import { provideRouter } from '@angular/router';

describe('ClientDetail', () => {
  let component: ClientDetail;
  let fixture: ComponentFixture<ClientDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientDetail],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(ClientDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
