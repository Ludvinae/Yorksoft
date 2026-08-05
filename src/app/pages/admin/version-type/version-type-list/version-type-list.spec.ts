import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VersionTypeList } from './version-type-list';
import { provideRouter } from '@angular/router';

describe('VersionTypeList', () => {
  let component: VersionTypeList;
  let fixture: ComponentFixture<VersionTypeList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VersionTypeList],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(VersionTypeList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
