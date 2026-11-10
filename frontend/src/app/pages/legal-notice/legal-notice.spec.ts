import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';

import { LegalNotice } from './legal-notice';

describe('LegalNotice', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LegalNotice],
      providers: [provideRouter([])],
    }).compileComponents();
  });

  it('names the publisher, the host and the contact address', async () => {
    const fixture = TestBed.createComponent(LegalNotice);
    await fixture.whenStable();
    const text = (fixture.nativeElement as HTMLElement).textContent ?? '';

    expect(text).toContain('Benoit Champlon');
    expect(text).toContain('netcup GmbH');
    expect(text).toContain('contact@benoitchamplon.fr');
  });
});
