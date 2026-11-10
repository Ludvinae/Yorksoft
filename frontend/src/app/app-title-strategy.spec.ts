import { TestBed } from '@angular/core/testing';
import { Title } from '@angular/platform-browser';
import { RouterStateSnapshot } from '@angular/router';
import { AppTitleStrategy } from './app-title-strategy';

describe('AppTitleStrategy', () => {
  let strategy: AppTitleStrategy;
  let title: Title;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    strategy = TestBed.inject(AppTitleStrategy);
    title = TestBed.inject(Title);
  });

  it('suffixes the route title with the app name', () => {
    vi.spyOn(strategy, 'buildTitle').mockReturnValue('Tableau de bord');
    strategy.updateTitle({} as RouterStateSnapshot);
    expect(title.getTitle()).toBe('Tableau de bord · Yorksoft');
  });

  it('shows only the app name for a route without a title', () => {
    vi.spyOn(strategy, 'buildTitle').mockReturnValue(undefined);
    strategy.updateTitle({} as RouterStateSnapshot);
    expect(title.getTitle()).toBe('Yorksoft');
  });
});
