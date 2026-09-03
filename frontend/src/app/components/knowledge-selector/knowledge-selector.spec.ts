import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KnowledgeSelector } from './knowledge-selector';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

describe('KnowledgeSelector', () => {
  let component: KnowledgeSelector;
  let fixture: ComponentFixture<KnowledgeSelector>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KnowledgeSelector],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(KnowledgeSelector);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
