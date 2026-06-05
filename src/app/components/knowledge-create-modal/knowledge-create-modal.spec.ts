import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KnowledgeCreateModal } from './knowledge-create-modal';

describe('KnowledgeCreateModal', () => {
  let component: KnowledgeCreateModal;
  let fixture: ComponentFixture<KnowledgeCreateModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KnowledgeCreateModal],
    }).compileComponents();

    fixture = TestBed.createComponent(KnowledgeCreateModal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
