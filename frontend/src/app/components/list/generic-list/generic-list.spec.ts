import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericList } from './generic-list';
import { provideRouter } from '@angular/router';

describe('List', () => {
  let component: GenericList<any>;
  let fixture: ComponentFixture<GenericList<any>>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenericList],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(GenericList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
