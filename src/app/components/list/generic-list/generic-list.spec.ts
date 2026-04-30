import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericList } from './generic-list';

describe('List', () => {
  let component: GenericList;
  let fixture: ComponentFixture<GenericList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenericList],
    }).compileComponents();

    fixture = TestBed.createComponent(GenericList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
