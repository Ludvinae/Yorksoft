import { Component, inject, Signal } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { KpiCard } from '../cards/kpi-card/kpi-card';
import { TicketStatusPieChart } from '../cards/ticket-status-pie-chart/ticket-status-pie-chart';
import { DashboardService } from '../../../services/business/dashboard-service';

@Component({
  selector: 'app-technician-dashboard',
  imports: [DecimalPipe, KpiCard, TicketStatusPieChart],
  templateUrl: './technician-dashboard.html',
  styleUrl: './technician-dashboard.css',
})
export class TechnicianDashboard {
  dashboardService = inject(DashboardService);

  readonly dashboard = this.dashboardService
    .dashboard as Signal<DashboardTechnicianDto | null>;
}
