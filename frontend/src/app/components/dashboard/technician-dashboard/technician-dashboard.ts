import { Component, inject, OnInit, Signal, signal } from '@angular/core';
import { KpiCard } from '../cards/kpi-card/kpi-card';
import { DashboardService } from '../../../services/business/dashboard-service';
import { TicketStoreService } from '../../../services/business/ticket-store-service';
import { AuthService } from '../../../services/business/auth-service';
import { DecimalPipe, PercentPipe } from '@angular/common';
import { ActivityFeed } from '../../activity-feed/activity-feed';
import { TechnicianEvolutionChart } from '../cards/technician-evolution-chart/technician-evolution-chart';
import { TechnicianStatusChart } from '../cards/technician-status-chart/technician-status-chart';

// Same threshold as the backend's "critical" count (TicketDao.countAssignedCriticalTickets)
const CRITICAL_PRIORITY = 75;

@Component({
  selector: 'app-technician-dashboard',
  imports: [KpiCard, DecimalPipe, ActivityFeed, PercentPipe, TechnicianEvolutionChart, TechnicianStatusChart],
  templateUrl: './technician-dashboard.html',
  styleUrl: './technician-dashboard.css',
})
export class TechnicianDashboard implements OnInit {
  dashboardService = inject(DashboardService);
  ticketStore = inject(TicketStoreService);
  auth = inject(AuthService);

  readonly dashboard = this.dashboardService.dashboard as Signal<DashboardTechnicianDto | null>;
  readonly loading = this.dashboardService.loading;

  // Period of the statistics and of the evolution chart (the workload row is always "now")
  readonly periods = [
    { days: 7, label: '7 jours' },
    { days: 30, label: '30 jours' },
    { days: 90, label: '90 jours' },
    { days: 365, label: '1 an' },
  ];
  readonly timeframe = signal(30);

  readonly criticalPriority = CRITICAL_PRIORITY;

  ngOnInit(): void {
    this.dashboardService.loadDashboard(this.timeframe());
  }

  selectPeriod(days: number): void {
    if (days === this.timeframe()) return;
    this.timeframe.set(days);
    this.dashboardService.loadDashboard(days);
  }

  periodLabel(): string {
    return this.periods.find((p) => p.days === this.timeframe())?.label ?? `${this.timeframe()} jours`;
  }

  // Seconds to a short readable duration: "45 min", "2,5 h", "3,2 j"
  formatDuration(seconds: number | null): string | null {
    if (seconds == null) return null;
    const minutes = seconds / 60;
    if (minutes < 60) return `${Math.round(minutes)} min`;
    const hours = minutes / 60;
    if (hours < 24) return `${hours.toLocaleString('fr-FR', { maximumFractionDigits: 1 })} h`;
    return `${(hours / 24).toLocaleString('fr-FR', { maximumFractionDigits: 1 })} j`;
  }

  readonly jwt = this.auth.jwtInfo;
  today = this.toLocalDate(new Date());

  startOfWeek = (() => {
    const date = new Date();
    date.setDate(date.getDate() - ((date.getDay() + 6) % 7));
    return this.toLocalDate(date);
  })();

  private toLocalDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
  }
}
