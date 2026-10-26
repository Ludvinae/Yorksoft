import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CenteredPage } from './centered-page';

describe('CenteredPage', () => {
  let component: CenteredPage;
  let fixture: ComponentFixture<CenteredPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CenteredPage],
    }).compileComponents();

    fixture = TestBed.createComponent(CenteredPage);
    component = fixture.componentInstance;
    component.title = 'Titre';
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render the title', () => {
    fixture.detectChanges();
    const h1: HTMLElement = fixture.nativeElement.querySelector('h1');
    expect(h1.textContent).toContain('Titre');
  });
});
