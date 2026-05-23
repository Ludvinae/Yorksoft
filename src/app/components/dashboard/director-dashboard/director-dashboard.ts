import { Component, inject, OnInit, Signal } from '@angular/core';
import { DashboardService } from '../../../services/business/dashboard-service';

@Component({
  selector: 'app-director-dashboard',
  imports: [],
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
