import { Component, inject, OnInit, Signal } from '@angular/core';
import { KpiCard } from '../cards/kpi-card/kpi-card';
import { DashboardService } from '../../../services/business/dashboard-service';

@Component({
  selector: 'app-admin-dashboard',
  imports: [KpiCard],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css',
})
export class AdminDashboard implements OnInit {
  dashboardService = inject(DashboardService);

  readonly dashboard = this.dashboardService.dashboard as Signal<DashboardAdminDto | null>;

  ngOnInit(): void {
    this.dashboardService.loadDashboard(365);
  }
}
