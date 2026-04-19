import { Component, inject, Input } from '@angular/core';
import { BreakpointSwitch } from '../../../services/breakpoint-switch';
import { HeaderMobile } from '../header-mobile/header-mobile';
import { HeaderDesktop } from '../header-desktop/header-desktop';
import { Auth } from '../../../services/auth';

@Component({
  selector: 'app-header',
  imports: [HeaderMobile, HeaderDesktop],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  private breakpointObserver = inject(BreakpointSwitch);

  isMobile = this.breakpointObserver.isMobile;
}
