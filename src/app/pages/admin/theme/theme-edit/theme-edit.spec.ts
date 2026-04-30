import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThemeEdit } from './theme-edit';

describe('ThemeEdit', () => {
  let component: ThemeEdit;
  let fixture: ComponentFixture<ThemeEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ThemeEdit],
    }).compileComponents();

    fixture = TestBed.createComponent(ThemeEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
