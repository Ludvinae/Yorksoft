import { TestBed } from '@angular/core/testing';

import { Avatar } from './avatar';

describe('Avatar', () => {
  async function render(name: string | null | undefined): Promise<string> {
    const fixture = TestBed.createComponent(Avatar);
    fixture.componentRef.setInput('name', name);
    await fixture.whenStable();
    return (fixture.nativeElement as HTMLElement).textContent?.trim() ?? '';
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({ imports: [Avatar] }).compileComponents();
  });

  it('shows the initials of the first two words', async () => {
    expect(await render('jean  dupont martin')).toBe('JD');
  });

  it('shows a single initial for a one-word name', async () => {
    expect(await render('Visiteur')).toBe('V');
  });

  it('shows nothing without a name', async () => {
    expect(await render(null)).toBe('');
  });
});
