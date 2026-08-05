import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientEdit } from './client-edit';
import { provideRouter } from '@angular/router';

describe('ClientEdit', () => {
  let component: ClientEdit;
  let fixture: ComponentFixture<ClientEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientEdit],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(ClientEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
