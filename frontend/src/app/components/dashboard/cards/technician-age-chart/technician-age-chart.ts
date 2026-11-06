import { Component, computed, input } from '@angular/core';
import { ChartConfiguration, ChartData } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { readChartTheme } from '../chart-theme';

// Same order as the backend's activeTicketsByAge buckets
const AGE_BANDS = ['Moins de 1 j', '1 à 3 j', '3 à 7 j', '7 j et plus'];

/**
 * Technician's active tickets by age since opening. Age bands are ordered, so they take a one-hue ramp
 * that darkens with age: the tickets that linger stand out.
 */
@Component({
  selector: 'app-technician-age-chart',
  imports: [BaseChartDirective],
  templateUrl: './technician-age-chart.html',
})
export class TechnicianAgeChart {
  counts = input.required<number[]>();

  private readonly theme = readChartTheme();

  readonly rows = computed(() => AGE_BANDS.map((label, i) => ({ label, count: this.counts()[i] ?? 0 })));

  readonly isEmpty = computed(() => this.rows().every((row) => row.count === 0));

  readonly chartData = computed<ChartData<'bar'>>(() => ({
    labels: this.rows().map((row) => row.label),
    datasets: [
      {
        label: 'Tickets actifs',
        data: this.rows().map((row) => row.count),
        backgroundColor: this.theme.ordinal,
        maxBarThickness: 24,
        borderRadius: 4,
        borderSkipped: 'start',
      },
    ],
  }));

  readonly chartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: { legend: { display: false } },
    scales: {
      x: {
        grid: { display: false },
        ticks: { color: this.theme.text },
      },
      y: {
        beginAtZero: true,
        grid: { color: this.theme.grid },
        border: { display: false },
        ticks: { color: this.theme.text, precision: 0 },
      },
    },
  };
}
