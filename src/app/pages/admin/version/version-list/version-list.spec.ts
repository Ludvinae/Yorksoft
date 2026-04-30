import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VersionList } from './version-list';

describe('VersionList', () => {
  let component: VersionList;
  let fixture: ComponentFixture<VersionList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VersionList],
    }).compileComponents();

    fixture = TestBed.createComponent(VersionList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
