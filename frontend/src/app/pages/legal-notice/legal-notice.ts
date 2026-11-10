import { Component } from '@angular/core';
import { BackButton } from '../../components/layout/back-button/back-button';

/**
 * Legal notice and privacy policy, mandatory for a public site in France.
 * Public route (no guard). Keep the personal data section consistent with the data
 * processing agreement signed with netcup (docs/SERVER_SETUP.md, section 6).
 */
@Component({
  selector: 'app-legal-notice',
  imports: [BackButton],
  templateUrl: './legal-notice.html',
})
export class LegalNotice {
  protected readonly contactEmail = 'contact@benoitchamplon.fr';
}
