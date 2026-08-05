import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImpactList } from './impact-list';
import { provideRouter } from '@angular/router';

describe('ImpactList', () => {
  let component: ImpactList;
  let fixture: ComponentFixture<ImpactList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImpactList],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(ImpactList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
