import { Component, inject, OnInit, Signal } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { KpiCard } from '../cards/kpi-card/kpi-card';
import { TicketStatusPieChart } from '../cards/ticket-status-pie-chart/ticket-status-pie-chart';
import { DashboardService } from '../../../services/business/dashboard-service';

@Component({
  selector: 'app-manager-dashboard',
  imports: [DecimalPipe, KpiCard, TicketStatusPieChart],
  templateUrl: './manager-dashboard.html',
  styleUrl: './manager-dashboard.css',
})
export class ManagerDashboard implements OnInit {
  dashboardService = inject(DashboardService);

  readonly dashboard = this.dashboardService.dashboard as Signal<DashboardManagerDto | null>;

  ngOnInit(): void {
    this.dashboardService.loadDashboard(365);
  }
}
