import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminMenuButton } from './admin-menu-button';

describe('AdminMenuButton', () => {
  let component: AdminMenuButton;
  let fixture: ComponentFixture<AdminMenuButton>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminMenuButton],
    }).compileComponents();

    fixture = TestBed.createComponent(AdminMenuButton);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
