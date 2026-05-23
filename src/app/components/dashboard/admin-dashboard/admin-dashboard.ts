import { Component, inject, Signal } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { KpiCard } from '../cards/kpi-card/kpi-card';
import { TicketStatusPieChart } from '../cards/ticket-status-pie-chart/ticket-status-pie-chart';
import { DashboardService } from '../../../services/business/dashboard-service';

@Component({
  selector: 'app-admin-dashboard',
  imports: [DecimalPipe, KpiCard, TicketStatusPieChart],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css',
})
export class AdminDashboard {
  dashboardService = inject(DashboardService);

  readonly dashboard = this.dashboardService
    .dashboard as Signal<DashboardAdminDto | null>;
}
