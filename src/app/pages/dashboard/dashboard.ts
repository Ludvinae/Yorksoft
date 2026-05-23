import { Component, inject, OnInit } from '@angular/core';
import { DatePipe, DecimalPipe } from '@angular/common';
import { KpiCard } from '../../components/dashboard/kpi-card/kpi-card';
import { DashboardService } from '../../services/business/dashboard-service';
import { TicketStatusPieChart } from '../../components/dashboard/ticket-status-pie-chart/ticket-status-pie-chart';

@Component({
  selector: 'app-dashboard',
  imports: [DatePipe, KpiCard, TicketStatusPieChart, DecimalPipe],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  dashboardService = inject(DashboardService);

  currentDate = new Date();

  ngOnInit(): void {
    this.dashboardService.loadDashboard(365);
  }
}
