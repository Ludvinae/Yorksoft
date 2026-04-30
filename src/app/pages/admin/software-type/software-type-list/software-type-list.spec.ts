import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SoftwareTypeList } from './software-type-list';

describe('SoftwareTypeList', () => {
  let component: SoftwareTypeList;
  let fixture: ComponentFixture<SoftwareTypeList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SoftwareTypeList],
    }).compileComponents();

    fixture = TestBed.createComponent(SoftwareTypeList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
