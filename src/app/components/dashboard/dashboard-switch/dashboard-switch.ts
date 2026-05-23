import { Component, inject } from '@angular/core';
import { AuthService } from '../../../services/business/auth-service';
import { AdminDashboard } from '../admin-dashboard/admin-dashboard';
import { ManagerDashboard } from '../manager-dashboard/manager-dashboard';
import { TechnicianDashboard } from '../technician-dashboard/technician-dashboard';
import { DirectorDashboard } from '../director-dashboard/director-dashboard';

@Component({
  selector: 'app-dashboard-switch',
  imports: [AdminDashboard, ManagerDashboard, TechnicianDashboard, DirectorDashboard],
  templateUrl: './dashboard-switch.html',
  styleUrl: './dashboard-switch.css',
})
export class DashboardSwitch {
  auth = inject(AuthService);
}
