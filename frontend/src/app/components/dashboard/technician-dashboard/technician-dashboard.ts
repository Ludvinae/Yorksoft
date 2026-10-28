import { Component, inject, OnInit, Signal } from '@angular/core';
import { KpiCard } from '../cards/kpi-card/kpi-card';
import { DashboardService } from '../../../services/business/dashboard-service';
import { TicketStoreService } from '../../../services/business/ticket-store-service';
import { AuthService } from '../../../services/business/auth-service';
import { DecimalPipe, PercentPipe} from '@angular/common';
import {ActivityFeed} from '../../activity-feed/activity-feed';

@Component({
  selector: 'app-technician-dashboard',
  imports: [KpiCard, DecimalPipe, ActivityFeed, PercentPipe],
  templateUrl: './technician-dashboard.html',
  styleUrl: './technician-dashboard.css',
})
export class TechnicianDashboard implements OnInit {
  dashboardService = inject(DashboardService);
  ticketStore = inject(TicketStoreService);
  auth = inject(AuthService);

  readonly dashboard = this.dashboardService.dashboard as Signal<DashboardTechnicianDto | null>;

  ngOnInit(): void {
    this.dashboardService.loadDashboard(365);
  }

  readonly jwt = this.auth.jwtInfo;
  protected readonly Date = Date;
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
