import { Component, inject, Input } from '@angular/core';
import { BreakpointSwitch } from '../../../services/breakpoint-switch';
import { MenuMobile } from '../menu-mobile/menu-mobile';
import { MenuDesktop } from '../menu-desktop/menu-desktop';
import { Auth } from '../../../services/auth';

@Component({
  selector: 'app-header',
  imports: [MenuMobile, MenuDesktop],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  private breakpointObserver = inject(BreakpointSwitch);

  isMobile = this.breakpointObserver.isMobile;

  @Input() auth!: boolean;
}
