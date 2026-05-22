import { Component } from '@angular/core';
import { DatePipe } from '@angular/common';
import { KpiCard } from '../../components/dashboard/kpi-card/kpi-card';

@Component({
  selector: 'app-dashboard',
  imports: [DatePipe, KpiCard],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  currentDate = new Date();


}
