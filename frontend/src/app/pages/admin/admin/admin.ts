import { Component } from '@angular/core';
import { AdminMenu, AdminMenuGroup } from '../../../components/admin/admin-menu/admin-menu';
import { PageHeader } from '../../../components/layout/page-header/page-header';

@Component({
  selector: 'app-admin',
  imports: [AdminMenu, PageHeader],
  templateUrl: './admin.html',
  styleUrl: './admin.css',
})
export class Admin {
  baseRoute: string = '/admin';

  menuGroups: AdminMenuGroup[] = [
    {
      title: 'Logiciels',
      items: [
        { label: 'Logiciels', link: this.baseRoute + '/software' },
        { label: 'Types de logiciel', link: this.baseRoute + '/software-type' },
        { label: 'Versions de logiciel', link: this.baseRoute + '/version' },
        { label: 'Types de version', link: this.baseRoute + '/version-type' },
      ],
    },
    {
      title: 'Tickets',
      items: [
        { label: "Niveaux d'impact", link: this.baseRoute + '/impact' },
        { label: "Niveaux d'urgence", link: this.baseRoute + '/urgency' },
        { label: 'Thématiques', link: this.baseRoute + '/theme' },
        { label: 'Statuts de ticket', link: this.baseRoute + '/status' },
      ],
    },
    {
      title: 'Utilisateurs',
      items: [{ label: 'Nouvel utilisateur', link: this.baseRoute + '/user/create' }],
    },
  ];
}
