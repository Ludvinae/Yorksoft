import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { NotFound } from './pages/not-found/not-found';
import { Knowledge } from './pages/knowledge/knowledge';
import { Settings } from './pages/settings/settings';
import { Dashboard } from './pages/dashboard/dashboard';
import { SoftwareEdit } from './pages/admin/software/software-edit/software-edit';
import { Admin } from './pages/admin/admin/admin';
import { SoftwareDetail } from './pages/admin/software/software-detail/software-detail';
import { SoftwareList } from './pages/admin/software/software-list/software-list';
import { SoftwareTypeList } from './pages/admin/software-type/software-type-list/software-type-list';
import { SoftwareTypeEdit } from './pages/admin/software-type/software-type-edit/software-type-edit';
import { SoftwareTypeDetail } from './pages/admin/software-type/software-type-detail/software-type-detail';
import { ImpactList } from './pages/admin/impact/impact-list/impact-list';
import { ImpactEdit } from './pages/admin/impact/impact-edit/impact-edit';
import { ImpactDetail } from './pages/admin/impact/impact-detail/impact-detail';
import { UrgencyList } from './pages/admin/urgency/urgency-list/urgency-list';
import { UrgencyEdit } from './pages/admin/urgency/urgency-edit/urgency-edit';
import { UrgencyDetail } from './pages/admin/urgency/urgency-detail/urgency-detail';
import { ThemeList } from './pages/admin/theme/theme-list/theme-list';
import { ThemeEdit } from './pages/admin/theme/theme-edit/theme-edit';
import { ThemeDetail } from './pages/admin/theme/theme-detail/theme-detail';
import { StatusList } from './pages/admin/status/status-list/status-list';
import { StatusEdit } from './pages/admin/status/status-edit/status-edit';
import { StatusDetail } from './pages/admin/status/status-detail/status-detail';
import { VersionList } from './pages/admin/version/version-list/version-list';
import { VersionEdit } from './pages/admin/version/version-edit/version-edit';
import { VersionDetail } from './pages/admin/version/version-detail/version-detail';
import { VersionTypeList } from './pages/admin/version-type/version-type-list/version-type-list';
import { VersionTypeEdit } from './pages/admin/version-type/version-type-edit/version-type-edit';
import { VersionTypeDetail } from './pages/admin/version-type/version-type-detail/version-type-detail';
import { TicketList } from './pages/ticket/ticket-list/ticket-list';
import { TicketEdit } from './pages/ticket/ticket-edit/ticket-edit';
import { TicketDetail } from './pages/ticket/ticket-detail/ticket-detail';
import { Forbidden } from './pages/forbidden/forbidden';
import { technicianGuard } from './guards/technician-guard';
import { clientGuard } from './guards/client-guard';
import { employeeGuard } from './guards/employee-guard';
import { adminGuard } from './guards/admin-guard';
import { ClientList } from './pages/client/client-list/client-list';
import { TicketNew } from './pages/ticket/ticket-new/ticket-new';

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
    canActivate: [clientGuard],
  },
  {
    path: 'ticket',
    component: TicketList,
    title: 'Tickets',
    canActivate: [employeeGuard],
  },
  {
    path: 'ticket/create',
    component: TicketNew,
    title: 'Nouveau ticket',
    canActivate: [technicianGuard],
  },
  {
    path: 'ticket/update/:id',
    component: TicketEdit,
    title: 'Modification de ticket',
    canActivate: [technicianGuard],
  },
  {
    path: 'ticket/:id',
    component: TicketDetail,
    title: 'Details du ticket',
    canActivate: [technicianGuard],
  },
  {
    path: 'clients',
    component: ClientList,
    title: 'Clients',
    canActivate: [employeeGuard],
  },
  {
    path: 'knowledge',
    component: Knowledge,
    title: 'Base de connaissance',
    canActivate: [employeeGuard],
  },
  {
    path: 'settings',
    component: Settings,
    title: 'Réglages',
    canActivate: [clientGuard],
  },
  {
    path: 'admin',
    children: [
      { path: '', component: Admin, canActivate: [employeeGuard] },
      { path: 'software', component: SoftwareList, canActivate: [employeeGuard] },
      { path: 'software/create', component: SoftwareEdit, canActivate: [adminGuard] },
      { path: 'software/update/:id', component: SoftwareEdit, canActivate: [adminGuard] },
      { path: 'software/:id', component: SoftwareDetail, canActivate: [employeeGuard] },
      { path: 'software-type', component: SoftwareTypeList, canActivate: [employeeGuard] },
      { path: 'software-type/create', component: SoftwareTypeEdit, canActivate: [adminGuard] },
      { path: 'software-type/update/:id', component: SoftwareTypeEdit, canActivate: [adminGuard] },
      { path: 'software-type/:id', component: SoftwareTypeDetail, canActivate: [employeeGuard] },
      { path: 'impact', component: ImpactList, canActivate: [employeeGuard] },
      { path: 'impact/create', component: ImpactEdit, canActivate: [adminGuard] },
      { path: 'impact/update/:id', component: ImpactEdit, canActivate: [adminGuard] },
      { path: 'impact/:id', component: ImpactDetail, canActivate: [employeeGuard] },
      { path: 'urgency', component: UrgencyList, canActivate: [employeeGuard] },
      { path: 'urgency/create', component: UrgencyEdit, canActivate: [adminGuard] },
      { path: 'urgency/update/:id', component: UrgencyEdit, canActivate: [adminGuard] },
      { path: 'urgency/:id', component: UrgencyDetail, canActivate: [employeeGuard] },
      { path: 'theme', component: ThemeList, canActivate: [employeeGuard] },
      { path: 'theme/create', component: ThemeEdit, canActivate: [adminGuard] },
      { path: 'theme/update/:id', component: ThemeEdit, canActivate: [adminGuard] },
      { path: 'theme/:id', component: ThemeDetail, canActivate: [employeeGuard] },
      { path: 'status', component: StatusList, canActivate: [employeeGuard] },
      { path: 'status/create', component: StatusEdit, canActivate: [adminGuard] },
      { path: 'status/update/:id', component: StatusEdit, canActivate: [adminGuard] },
      { path: 'status/:id', component: StatusDetail, canActivate: [employeeGuard] },
      { path: 'version', component: VersionList, canActivate: [employeeGuard] },
      { path: 'version/create', component: VersionEdit, canActivate: [adminGuard] },
      { path: 'version/update/:id', component: VersionEdit, canActivate: [adminGuard] },
      { path: 'version/:id', component: VersionDetail, canActivate: [employeeGuard] },
      { path: 'version-type', component: VersionTypeList, canActivate: [employeeGuard] },
      { path: 'version-type/create', component: VersionTypeEdit, canActivate: [adminGuard] },
      { path: 'version-type/update/:id', component: VersionTypeEdit, canActivate: [adminGuard] },
      { path: 'version-type/:id', component: VersionTypeDetail, canActivate: [employeeGuard] },
    ],
  },
  {
    path: 'forbidden',
    component: Forbidden,
    title: 'Accès interdit',
  },
  {
    path: '**',
    component: NotFound,
    title: 'Page indisponible',
  },
];
