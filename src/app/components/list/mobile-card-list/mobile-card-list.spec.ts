import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MobileCardList } from './mobile-card-list';

describe('MobileCardList', () => {
  let component: MobileCardList;
  let fixture: ComponentFixture<MobileCardList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MobileCardList],
    }).compileComponents();

    fixture = TestBed.createComponent(MobileCardList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
