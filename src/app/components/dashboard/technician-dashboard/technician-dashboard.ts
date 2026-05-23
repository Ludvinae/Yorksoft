import { Component, inject, OnInit, Signal } from '@angular/core';
import { KpiCard } from '../cards/kpi-card/kpi-card';
import { DashboardService } from '../../../services/business/dashboard-service';
import { AuthService } from '../../../services/business/auth-service';

@Component({
  selector: 'app-technician-dashboard',
  imports: [KpiCard],
  templateUrl: './technician-dashboard.html',
  styleUrl: './technician-dashboard.css',
})
export class TechnicianDashboard implements OnInit {
  dashboardService = inject(DashboardService);
  auth = inject(AuthService);

  readonly dashboard = this.dashboardService.dashboard as Signal<DashboardTechnicianDto | null>;

  ngOnInit(): void {
    this.dashboardService.loadDashboard(365);
  }

  readonly jwt = this.auth.jwtInfo
}
