import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderEditModal } from './header-edit-modal';
import { provideRouter } from '@angular/router';

describe('HeaderEditModal', () => {
  let component: HeaderEditModal;
  let fixture: ComponentFixture<HeaderEditModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderEditModal],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(HeaderEditModal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
