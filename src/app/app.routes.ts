import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { NotFound } from './pages/not-found/not-found';
import { Tickets } from './pages/tickets/tickets';
import { Clients } from './pages/clients/clients';
import { Knowledge } from './pages/knowledge/knowledge';
import { Settings } from './pages/settings/settings';
import { NewTicket } from './pages/new-ticket/new-ticket';
import { Dashboard } from './pages/dashboard/dashboard';
import {SoftwareEdit} from './pages/admin/software/software-edit/software-edit';
import { Admin } from './pages/admin/admin/admin';
import { SoftwareDetail } from './pages/admin/software/software-detail/software-detail';
import { SoftwareList } from './pages/admin/software/software-list/software-list';
import { SoftwareTypeList } from './pages/admin/software-type/software-type-list/software-type-list';
import { SoftwareTypeEdit } from './pages/admin/software-type/software-type-edit/software-type-edit';
import { SoftwareTypeDetail } from './pages/admin/software-type/software-type-detail/software-type-detail';

export const routes: Routes = [
  {
    path: '',
    component: Login,
    title: 'Login',
  },
  {
    path: 'dashboard',
    component: Dashboard,
    title: 'Tableau de bord',
  },
  {
    path: 'tickets',
    component: Tickets,
    title: 'Tickets',
  },
  {
    path: 'new-ticket',
    component: NewTicket,
    title: 'Nouveau ticket',
  },
  {
    path: 'clients',
    component: Clients,
    title: 'Clients',
  },
  {
    path: 'knowledge',
    component: Knowledge,
    title: 'Base de connaissance',
  },
  {
    path: 'settings',
    component: Settings,
    title: 'Réglages',
  },
  {
    path: 'admin',
    children: [
      { path: '', component: Admin },
      { path: 'software', component: SoftwareList },
      { path: 'software/create', component: SoftwareEdit },
      { path: 'software/update/:id', component: SoftwareEdit },
      { path: 'software/:id', component: SoftwareDetail },
      { path: 'software-type', component: SoftwareTypeList },
      { path: 'software-type/create', component: SoftwareTypeEdit },
      { path: 'software-type/update/:id', component: SoftwareTypeEdit },
      { path: 'software-type/:id', component: SoftwareTypeDetail },
    ],
  },
  {
    path: '**',
    component: NotFound,
    title: 'Not Found',
  },
];
