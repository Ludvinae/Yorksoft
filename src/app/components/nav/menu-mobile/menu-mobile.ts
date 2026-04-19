import { Component } from '@angular/core';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-menu-mobile',
  imports: [DatePipe],
  templateUrl: './menu-mobile.html',
  styleUrl: './menu-mobile.css',
})
export class MenuMobile {
  today = new Date();

  toggleMenu() {
    console.log('open sidebar mobile');
    // ici tu peux ouvrir un drawer DaisyUI
  }
}
