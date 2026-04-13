import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { NotFound } from './pages/not-found/not-found';
import { Tickets } from './pages/tickets/tickets';
import { Clients } from './pages/clients/clients';
import { Knowledge } from './pages/knowledge/knowledge';
import { Settings } from './pages/settings/settings';

export const routes: Routes = [
  {
    path: '',
    component: Home,
    title: 'Home',
  },
  {
    path: 'tickets',
    component: Tickets,
    title: 'Tickets',
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
