import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailPage } from './detail-page';
import { provideRouter } from '@angular/router';

describe('DetailPage', () => {
  let component: DetailPage;
  let fixture: ComponentFixture<DetailPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailPage],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(DetailPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
