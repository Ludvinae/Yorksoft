import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StatusList } from './status-list';
import { provideRouter } from '@angular/router';

describe('StatusList', () => {
  let component: StatusList;
  let fixture: ComponentFixture<StatusList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StatusList],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(StatusList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
