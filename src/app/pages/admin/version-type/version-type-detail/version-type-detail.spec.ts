import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VersionTypeDetail } from './version-type-detail';

describe('VersionTypeDetail', () => {
  let component: VersionTypeDetail;
  let fixture: ComponentFixture<VersionTypeDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VersionTypeDetail],
    }).compileComponents();

    fixture = TestBed.createComponent(VersionTypeDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
