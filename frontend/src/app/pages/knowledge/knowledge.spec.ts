import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Knowledge } from './knowledge';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';

const article: KnowledgeArticle = {
  idKnowledge: 1,
  subject: 'Boîte mail saturée',
  description: 'Quota atteint',
  resolution: 'Archiver des messages',
  idTheme: 2,
  themeName: 'Bug',
  authorName: 'Sandra Schmidt',
  versions: ['Logiciel 1.0'],
  procedures: [],
};

describe('Knowledge', () => {
  let component: Knowledge;
  let fixture: ComponentFixture<Knowledge>;
  let http: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Knowledge],
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(Knowledge);
    component = fixture.componentInstance;
    http = TestBed.inject(HttpTestingController);
    fixture.detectChanges();

    http.expectOne((req) => req.url.endsWith('/theme/list')).flush([]);
  });

  afterEach(() => http.verify());

  it('shows the articles with their author', () => {
    http.expectOne((req) => req.url.endsWith('/knowledge/articles')).flush([article]);
    fixture.detectChanges();

    const text = fixture.nativeElement.textContent as string;
    expect(text).toContain('Boîte mail saturée');
    expect(text).toContain('Créé par Sandra Schmidt');
  });

  it('filters the articles by theme', () => {
    http.expectOne((req) => req.url.endsWith('/knowledge/articles')).flush([article]);

    component.onThemeChange(2);

    const req = http.expectOne((r) => r.url.endsWith('/knowledge/articles'));
    expect(req.request.params.get('idTheme')).toBe('2');
    req.flush([]);
  });
});
