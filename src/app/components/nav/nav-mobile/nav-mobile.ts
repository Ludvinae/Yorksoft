import { Component, EventEmitter, inject, Output } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { Auth } from '../../../services/auth';

@Component({
  selector: 'app-nav-mobile',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './nav-mobile.html',
  styleUrl: './nav-mobile.css',
})
export class NavMobile {
  private auth = inject(Auth);

  @Output() navigateEvent = new EventEmitter<void>();

  constructor(private router: Router) {}

  navigate() {
    this.navigateEvent.emit(); // ferme le drawer
  }

  close() {
    this.navigateEvent.emit(); // bouton X
  }

  logout() {
    console.log('logout');
    this.navigateEvent.emit();
    this.auth.logout();
    this.router.navigate(['/']);
  }
}
