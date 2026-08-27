import { Component } from '@angular/core';
import { AdminMenu, AdminMenuItems } from '../../../components/admin/admin-menu/admin-menu';

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
    { label: 'Versions de logiciel', link: this.baseRoute + '/version' },
    { label: 'Types de version', link: this.baseRoute + '/version-type' },
    { label: "Niveaux d'impact", link: this.baseRoute + '/impact' },
    { label: "Niveaux d'urgence", link: this.baseRoute + '/urgency' },
    { label: 'Thématiques', link: this.baseRoute + '/theme' },
    { label: 'Statuts de ticket', link: this.baseRoute + '/status' },
    { label: 'Utilisateurs', link: this.baseRoute + '/user/create' },
  ];
}
