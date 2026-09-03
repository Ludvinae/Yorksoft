import { Routes } from '@angular/router';
import { employeeGuard } from '../../guards/employee-guard';
import { adminGuard } from '../../guards/admin-guard';

export const ADMIN_ROUTES: Routes = [
  {
    path: 'user/create',
    loadComponent: () => import('./user/user-create/user-create').then((m) => m.UserCreate),
    canActivate: [adminGuard],
  },
  {
    path: '',
    loadComponent: () => import('./admin/admin').then((m) => m.Admin),
    canActivate: [employeeGuard],
  },
  {
    path: 'software',
    loadComponent: () =>
      import('./software/software-list/software-list').then((m) => m.SoftwareList),
    canActivate: [employeeGuard],
  },
  {
    path: 'software/create',
    loadComponent: () =>
      import('./software/software-edit/software-edit').then((m) => m.SoftwareEdit),
    canActivate: [adminGuard],
  },
  {
    path: 'software/update/:id',
    loadComponent: () =>
      import('./software/software-edit/software-edit').then((m) => m.SoftwareEdit),
    canActivate: [adminGuard],
  },
  {
    path: 'software/:id',
    loadComponent: () =>
      import('./software/software-detail/software-detail').then((m) => m.SoftwareDetail),
    canActivate: [employeeGuard],
  },
  {
    path: 'software-type',
    loadComponent: () =>
      import('./software-type/software-type-list/software-type-list').then(
        (m) => m.SoftwareTypeList,
      ),
    canActivate: [employeeGuard],
  },
  {
    path: 'software-type/create',
    loadComponent: () =>
      import('./software-type/software-type-edit/software-type-edit').then(
        (m) => m.SoftwareTypeEdit,
      ),
    canActivate: [adminGuard],
  },
  {
    path: 'software-type/update/:id',
    loadComponent: () =>
      import('./software-type/software-type-edit/software-type-edit').then(
        (m) => m.SoftwareTypeEdit,
      ),
    canActivate: [adminGuard],
  },
  {
    path: 'software-type/:id',
    loadComponent: () =>
      import('./software-type/software-type-detail/software-type-detail').then(
        (m) => m.SoftwareTypeDetail,
      ),
    canActivate: [employeeGuard],
  },
  {
    path: 'impact',
    loadComponent: () => import('./impact/impact-list/impact-list').then((m) => m.ImpactList),
    canActivate: [employeeGuard],
  },
  {
    path: 'impact/create',
    loadComponent: () => import('./impact/impact-edit/impact-edit').then((m) => m.ImpactEdit),
    canActivate: [adminGuard],
  },
  {
    path: 'impact/update/:id',
    loadComponent: () => import('./impact/impact-edit/impact-edit').then((m) => m.ImpactEdit),
    canActivate: [adminGuard],
  },
  {
    path: 'impact/:id',
    loadComponent: () => import('./impact/impact-detail/impact-detail').then((m) => m.ImpactDetail),
    canActivate: [employeeGuard],
  },
  {
    path: 'urgency',
    loadComponent: () => import('./urgency/urgency-list/urgency-list').then((m) => m.UrgencyList),
    canActivate: [employeeGuard],
  },
  {
    path: 'urgency/create',
    loadComponent: () => import('./urgency/urgency-edit/urgency-edit').then((m) => m.UrgencyEdit),
    canActivate: [adminGuard],
  },
  {
    path: 'urgency/update/:id',
    loadComponent: () => import('./urgency/urgency-edit/urgency-edit').then((m) => m.UrgencyEdit),
    canActivate: [adminGuard],
  },
  {
    path: 'urgency/:id',
    loadComponent: () =>
      import('./urgency/urgency-detail/urgency-detail').then((m) => m.UrgencyDetail),
    canActivate: [employeeGuard],
  },
  {
    path: 'theme',
    loadComponent: () => import('./theme/theme-list/theme-list').then((m) => m.ThemeList),
    canActivate: [employeeGuard],
  },
  {
    path: 'theme/create',
    loadComponent: () => import('./theme/theme-edit/theme-edit').then((m) => m.ThemeEdit),
    canActivate: [adminGuard],
  },
  {
    path: 'theme/update/:id',
    loadComponent: () => import('./theme/theme-edit/theme-edit').then((m) => m.ThemeEdit),
    canActivate: [adminGuard],
  },
  {
    path: 'theme/:id',
    loadComponent: () => import('./theme/theme-detail/theme-detail').then((m) => m.ThemeDetail),
    canActivate: [employeeGuard],
  },
  {
    path: 'status',
    loadComponent: () => import('./status/status-list/status-list').then((m) => m.StatusList),
    canActivate: [employeeGuard],
  },
  {
    path: 'status/create',
    loadComponent: () => import('./status/status-edit/status-edit').then((m) => m.StatusEdit),
    canActivate: [adminGuard],
  },
  {
    path: 'status/update/:id',
    loadComponent: () => import('./status/status-edit/status-edit').then((m) => m.StatusEdit),
    canActivate: [adminGuard],
  },
  {
    path: 'status/:id',
    loadComponent: () => import('./status/status-detail/status-detail').then((m) => m.StatusDetail),
    canActivate: [employeeGuard],
  },
  {
    path: 'version',
    loadComponent: () => import('./version/version-list/version-list').then((m) => m.VersionList),
    canActivate: [employeeGuard],
  },
  {
    path: 'version/create',
    loadComponent: () => import('./version/version-edit/version-edit').then((m) => m.VersionEdit),
    canActivate: [adminGuard],
  },
  {
    path: 'version/update/:id',
    loadComponent: () => import('./version/version-edit/version-edit').then((m) => m.VersionEdit),
    canActivate: [adminGuard],
  },
  {
    path: 'version/:id',
    loadComponent: () =>
      import('./version/version-detail/version-detail').then((m) => m.VersionDetail),
    canActivate: [employeeGuard],
  },
  {
    path: 'version-type',
    loadComponent: () =>
      import('./version-type/version-type-list/version-type-list').then((m) => m.VersionTypeList),
    canActivate: [employeeGuard],
  },
  {
    path: 'version-type/create',
    loadComponent: () =>
      import('./version-type/version-type-edit/version-type-edit').then((m) => m.VersionTypeEdit),
    canActivate: [adminGuard],
  },
  {
    path: 'version-type/update/:id',
    loadComponent: () =>
      import('./version-type/version-type-edit/version-type-edit').then((m) => m.VersionTypeEdit),
    canActivate: [adminGuard],
  },
  {
    path: 'version-type/:id',
    loadComponent: () =>
      import('./version-type/version-type-detail/version-type-detail').then((m) => m.VersionTypeDetail),
    canActivate: [employeeGuard],
  },
];
