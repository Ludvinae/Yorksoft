import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentCard } from './comment-card';
import { provideRouter } from '@angular/router';

describe('CommentCard', () => {
  let component: CommentCard;
  let fixture: ComponentFixture<CommentCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommentCard],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(CommentCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
