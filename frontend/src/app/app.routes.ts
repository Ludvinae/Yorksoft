import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { NotFound } from './pages/not-found/not-found';
import { Forbidden } from './pages/forbidden/forbidden';
import { technicianGuard } from './guards/technician-guard';
import { clientGuard } from './guards/client-guard';
import { employeeGuard } from './guards/employee-guard';

export const routes: Routes = [
  {
    path: '',
    component: Login,
    title: 'Connexion',
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./pages/dashboard/dashboard').then((m) => m.Dashboard),
    title: 'Tableau de bord',
    canActivate: [clientGuard],
  },

  {
    path: 'clients',
    loadComponent: () => import('./pages/client/client-list/client-list').then((m) => m.ClientList),
    title: 'Clients',
    canActivate: [employeeGuard],
  },
  {
    path: 'knowledge',
    loadComponent: () => import('./pages/knowledge/knowledge').then((m) => m.Knowledge),
    title: 'Base de connaissance',
    canActivate: [employeeGuard],
  },
  {
    path: 'settings',
    loadComponent: () => import('./pages/settings/settings').then((m) => m.Settings),
    title: 'Réglages',
    canActivate: [clientGuard],
  },
  {
    path: 'ticket',
    children: [
      {
        path: '',
        loadComponent: () =>
          import('./pages/ticket/ticket-list/ticket-list').then((m) => m.TicketList),
        canActivate: [employeeGuard],
      },
      {
        path: 'create',
        loadComponent: () =>
          import('./pages/ticket/ticket-new/ticket-new').then((m) => m.TicketNew),
        canActivate: [technicianGuard],
      },
      {
        path: 'update/:id',
        loadComponent: () =>
          import('./pages/ticket/ticket-edit/ticket-edit').then((m) => m.TicketEdit),
        canActivate: [technicianGuard],
      },
      {
        path: ':id',
        loadComponent: () =>
          import('./pages/ticket/ticket-detail/ticket-detail').then((m) => m.TicketDetail),
        canActivate: [technicianGuard],
      },
    ],
  },
  {
    path: 'admin',
    canActivate: [employeeGuard],
    loadChildren: () =>
      import('./pages/admin/admin.routes')
        .then(m => m.ADMIN_ROUTES)
  },
  {
    // Public: mandatory legal notice, reachable without logging in
    path: 'mentions-legales',
    loadComponent: () => import('./pages/legal-notice/legal-notice').then((m) => m.LegalNotice),
    title: 'Mentions légales',
  },
  {
    path: 'forbidden',
    component: Forbidden,
    title: 'Accès interdit',
    canActivate: [clientGuard],
  },
  {
    path: '**',
    component: NotFound,
    title: 'Page indisponible',
    canActivate: [clientGuard],
  },
];
