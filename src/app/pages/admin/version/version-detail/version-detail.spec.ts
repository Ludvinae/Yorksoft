import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VersionDetail } from './version-detail';

describe('VersionDetail', () => {
  let component: VersionDetail;
  let fixture: ComponentFixture<VersionDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VersionDetail],
    }).compileComponents();

    fixture = TestBed.createComponent(VersionDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
