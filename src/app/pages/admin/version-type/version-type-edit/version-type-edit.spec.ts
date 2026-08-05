import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VersionTypeEdit } from './version-type-edit';
import { provideRouter } from '@angular/router';

describe('VersionTypeEdit', () => {
  let component: VersionTypeEdit;
  let fixture: ComponentFixture<VersionTypeEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VersionTypeEdit],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(VersionTypeEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
