import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { DatePipe, TitleCasePipe } from '@angular/common';
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-header-mobile',
  imports: [DatePipe, TitleCasePipe],
  templateUrl: './header-mobile.html',
  styleUrl: './header-mobile.css',
})
export class HeaderMobile {
  protected auth = inject(AuthService);

  today = new Date();

  @Output() toggleMenu = new EventEmitter<void>();

  @Input() isOpen = false;
}
