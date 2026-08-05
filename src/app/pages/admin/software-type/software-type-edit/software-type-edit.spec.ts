import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SoftwareTypeEdit } from './software-type-edit';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('SoftwareTypeEdit', () => {
  let component: SoftwareTypeEdit;
  let fixture: ComponentFixture<SoftwareTypeEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SoftwareTypeEdit],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(SoftwareTypeEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
