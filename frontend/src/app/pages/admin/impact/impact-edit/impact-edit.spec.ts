import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImpactEdit } from './impact-edit';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('ImpactEdit', () => {
  let component: ImpactEdit;
  let fixture: ComponentFixture<ImpactEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImpactEdit],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(ImpactEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
