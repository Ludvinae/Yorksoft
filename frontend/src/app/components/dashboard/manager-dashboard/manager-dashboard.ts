import { Component, inject, OnInit, Signal } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { KpiCard } from '../cards/kpi-card/kpi-card';
import { TicketStatusPieChart } from '../cards/ticket-status-pie-chart/ticket-status-pie-chart';
import { DashboardService } from '../../../services/business/dashboard-service';
import { TicketStoreService } from '../../../services/business/ticket-store-service';
import { TechnicianWorkloadChart } from '../cards/technician-workload-chart/technician-workload-chart';
import { TicketSoftwareChart } from '../cards/ticket-software-chart/ticket-software-chart';
import { TicketThemeChart } from '../cards/ticket-theme-chart/ticket-theme-chart';
import {ActivityFeed} from '../../activity-feed/activity-feed';

@Component({
  selector: 'app-manager-dashboard',
  imports: [
    DecimalPipe,
    KpiCard,
    TicketStatusPieChart,
    TechnicianWorkloadChart,
    TicketSoftwareChart,
    TicketThemeChart,
    ActivityFeed,
  ],
  templateUrl: './manager-dashboard.html',
  styleUrl: './manager-dashboard.css',
})
export class ManagerDashboard implements OnInit {
  dashboardService = inject(DashboardService);
  ticketStore = inject(TicketStoreService);

  readonly dashboard = this.dashboardService.dashboard as Signal<DashboardManagerDto | null>;

  ngOnInit(): void {
    this.dashboardService.loadDashboard(365);
  }
}
