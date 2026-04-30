import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VersionTypeEdit } from './version-type-edit';

describe('VersionTypeEdit', () => {
  let component: VersionTypeEdit;
  let fixture: ComponentFixture<VersionTypeEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VersionTypeEdit],
    }).compileComponents();

    fixture = TestBed.createComponent(VersionTypeEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
