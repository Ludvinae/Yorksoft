import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { NotFound } from './pages/not-found/not-found';
import { Tickets } from './pages/tickets/tickets';
import { Clients } from './pages/clients/clients';
import { Knowledge } from './pages/knowledge/knowledge';
import { Settings } from './pages/settings/settings';
import { NewTicket } from './pages/new-ticket/new-ticket';
import { Dashboard } from './pages/dashboard/dashboard';

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
    title: 'Nouveau ticket'
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
    path: '**',
    component: NotFound,
    title: 'Not Found',
  },
];
