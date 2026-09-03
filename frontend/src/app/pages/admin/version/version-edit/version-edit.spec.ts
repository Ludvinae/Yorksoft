import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VersionEdit } from './version-edit';
import { provideRouter } from '@angular/router';
import {provideHttpClientTesting} from '@angular/common/http/testing';
import {provideHttpClient} from '@angular/common/http';

describe('VersionEdit', () => {
  let component: VersionEdit;
  let fixture: ComponentFixture<VersionEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VersionEdit],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(VersionEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
