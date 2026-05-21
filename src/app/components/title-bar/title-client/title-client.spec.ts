import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TitleClient } from './title-client';

describe('TitleClient', () => {
  let component: TitleClient;
  let fixture: ComponentFixture<TitleClient>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TitleClient],
    }).compileComponents();

    fixture = TestBed.createComponent(TitleClient);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
