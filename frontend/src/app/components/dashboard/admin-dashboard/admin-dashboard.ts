import { Component, computed, inject, OnInit, Signal } from '@angular/core';
import { KpiCard } from '../cards/kpi-card/kpi-card';
import { DashboardService } from '../../../services/business/dashboard-service';
import {ActivityFeed} from '../../activity-feed/activity-feed';

@Component({
  selector: 'app-admin-dashboard',
  imports: [KpiCard, ActivityFeed],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css',
})
export class AdminDashboard implements OnInit {
  dashboardService = inject(DashboardService);

  readonly dashboard = this.dashboardService.dashboard as Signal<DashboardAdminDto | null>;

  /** Above this many admin accounts, the "Comptes admin" KPI is highlighted. */
  static readonly MAX_ADMINS = 5;

  /** A single admin means no fallback if that account is lost; too many widens the attack surface. */
  readonly adminCountColor = computed(() => {
    const admins = this.dashboard()?.userHealth.admins;
    if (admins == null) return '';
    return admins <= 1 || admins > AdminDashboard.MAX_ADMINS ? 'text-warning' : 'text-success';
  });

  ngOnInit(): void {
    this.dashboardService.loadDashboard(365);
  }
}
