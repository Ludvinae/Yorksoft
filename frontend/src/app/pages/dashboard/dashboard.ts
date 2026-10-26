import { Component, inject, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { DashboardService } from '../../services/business/dashboard-service';
import { DashboardSwitch } from '../../components/dashboard/dashboard-switch/dashboard-switch';
import { PageHeader } from '../../components/layout/page-header/page-header';

@Component({
  selector: 'app-dashboard',
  imports: [DatePipe, DashboardSwitch, PageHeader],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {

  currentDate = new Date();

}
