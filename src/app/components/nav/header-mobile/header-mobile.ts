import { Component } from '@angular/core';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-header-mobile',
  imports: [DatePipe],
  templateUrl: './header-mobile.html',
  styleUrl: './header-mobile.css',
})
export class HeaderMobile {
  today = new Date();

  toggleMenu() {
    console.log('open sidebar mobile');
    // ici tu peux ouvrir un drawer DaisyUI
  }
}
