import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SoftwareDetail } from './software-detail';
import { provideRouter } from '@angular/router';

describe('SoftwareDetail', () => {
  let component: SoftwareDetail;
  let fixture: ComponentFixture<SoftwareDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SoftwareDetail],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(SoftwareDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
