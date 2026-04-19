import { Component, EventEmitter, Input, input, Output } from '@angular/core';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-header-mobile',
  imports: [DatePipe],
  templateUrl: './header-mobile.html',
  styleUrl: './header-mobile.css',
})
export class HeaderMobile {
  today = new Date();

  @Output() toggleMenu = new EventEmitter<void>();

  @Input() isOpen = false;

}
