import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderEditModal } from './header-edit-modal';

describe('HeaderEditModal', () => {
  let component: HeaderEditModal;
  let fixture: ComponentFixture<HeaderEditModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderEditModal],
    }).compileComponents();

    fixture = TestBed.createComponent(HeaderEditModal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
