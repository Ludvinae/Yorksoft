import { Component, inject } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/business/auth-service';

/**
 * "Retour" button for error pages: goes back to the previous in-app page, or to a
 * fallback (dashboard if logged in, home otherwise) when the app was opened directly
 * on this page — where history.back() would leave the app.
 */
@Component({
  selector: 'app-back-button',
  imports: [],
  templateUrl: './back-button.html',
  styleUrl: './back-button.css',
})
export class BackButton {
  private location = inject(Location);
  private router = inject(Router);
  private auth = inject(AuthService);

  goBack(): void {
    // Read at click time: by then the navigation to this page has completed, so
    // previousNavigation is the page the user came from (null on a direct load).
    if (this.router.lastSuccessfulNavigation()?.previousNavigation) {
      this.location.back();
      return;
    }

    this.router.navigateByUrl(this.auth.jwtInfo() ? '/dashboard' : '/');
  }
}
