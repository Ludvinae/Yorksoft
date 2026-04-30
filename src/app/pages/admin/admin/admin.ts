import { Component } from '@angular/core';
import { AdminMenu, AdminMenuItems } from '../../../components/admin/admin-menu/admin-menu';

;

@Component({
  selector: 'app-admin',
  imports: [AdminMenu],
  templateUrl: './admin.html',
  styleUrl: './admin.css',
})
export class Admin {
  baseRoute: string = '/admin';

  menuItems: AdminMenuItems[] = [
    { label: 'Logiciels', link: this.baseRoute + '/software' },
    { label: 'Types de logiciel', link: this.baseRoute + '/software-type' },
  ];
}
