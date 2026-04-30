import { Component, Input } from '@angular/core';
import { AdminMenuButton } from '../admin-menu-button/admin-menu-button';

export type AdminMenuItems = {
  label : string;
  link: string;
}


@Component({
  selector: 'app-admin-menu',
  imports: [AdminMenuButton],
  templateUrl: './admin-menu.html',
  styleUrl: './admin-menu.css',
})
export class AdminMenu {
  @Input({ required: true }) items!: AdminMenuItems[];
}
