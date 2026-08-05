import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MobileCardList } from './mobile-card-list';
import { provideRouter } from '@angular/router';

describe('MobileCardList', () => {
  let component: MobileCardList<any>;
  let fixture: ComponentFixture<MobileCardList<any>>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MobileCardList],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(MobileCardList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
