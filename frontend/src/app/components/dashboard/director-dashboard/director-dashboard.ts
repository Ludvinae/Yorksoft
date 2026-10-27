import { Component, inject, OnInit, Signal } from '@angular/core';
import { DashboardService } from '../../../services/business/dashboard-service';
import {ActivityFeed} from "../../activity-feed/activity-feed";
import { KpiCard } from '../cards/kpi-card/kpi-card';

@Component({
  selector: 'app-director-dashboard',
    imports: [
        ActivityFeed,
        KpiCard,
    ],
  templateUrl: './director-dashboard.html',
  styleUrl: './director-dashboard.css',
})
export class DirectorDashboard implements OnInit {
  dashboardService = inject(DashboardService);

  readonly dashboard = this.dashboardService.dashboard as Signal<DashboardDirectorDto | null>;

  ngOnInit(): void {
    this.dashboardService.loadDashboard(365);
  }
}
