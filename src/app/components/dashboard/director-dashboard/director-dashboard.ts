import { Component, inject, Signal } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { KpiCard } from '../cards/kpi-card/kpi-card';
import { TicketStatusPieChart } from '../cards/ticket-status-pie-chart/ticket-status-pie-chart';
import { DashboardService } from '../../../services/business/dashboard-service';

@Component({
  selector: 'app-director-dashboard',
  imports: [DecimalPipe, KpiCard, TicketStatusPieChart],
  templateUrl: './director-dashboard.html',
  styleUrl: './director-dashboard.css',
})
export class DirectorDashboard {
  dashboardService = inject(DashboardService);

  readonly dashboard = this.dashboardService
    .dashboard as Signal<DashboardDirectorDto | null>;
}
