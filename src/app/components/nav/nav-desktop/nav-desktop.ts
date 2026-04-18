import { Component, Input } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-nav-desktop',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './nav-desktop.html',
  styleUrl: './nav-desktop.css',
})
export class NavDesktop {
  @Input() router!: Router;

  protected isActive(route: string): boolean {
    return this.router.url.startsWith(route);
  }
}
