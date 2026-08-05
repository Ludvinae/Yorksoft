import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VersionDetail } from './version-detail';
import { provideRouter } from '@angular/router';

describe('VersionDetail', () => {
  let component: VersionDetail;
  let fixture: ComponentFixture<VersionDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VersionDetail],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(VersionDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
