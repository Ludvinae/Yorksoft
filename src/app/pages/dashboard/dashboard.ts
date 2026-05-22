import { Component, inject, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { KpiCard } from '../../components/dashboard/kpi-card/kpi-card';
import { DashboardService } from '../../services/business/dashboard-service';

@Component({
  selector: 'app-dashboard',
  imports: [DatePipe, KpiCard],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  dashboardService = inject(DashboardService);

  currentDate = new Date();

  ngOnInit(): void {
    this.dashboardService.loadDashboard();
  }
}
