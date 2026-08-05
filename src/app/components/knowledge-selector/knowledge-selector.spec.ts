import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KnowledgeSelector } from './knowledge-selector';
import { provideRouter } from '@angular/router';

describe('KnowledgeSelector', () => {
  let component: KnowledgeSelector;
  let fixture: ComponentFixture<KnowledgeSelector>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KnowledgeSelector],
      providers: [provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(KnowledgeSelector);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
