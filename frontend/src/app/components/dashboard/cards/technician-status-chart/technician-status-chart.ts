import { Component, computed, input } from '@angular/core';
import { ChartConfiguration, ChartData } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { StatusEnum } from '../../../../models/enum/StatusEnum';
import { readChartTheme } from '../chart-theme';

/**
 * Technician's active tickets by status, as horizontal bars in workflow order (one hue: the bars compare
 * amounts, the status names carry identity).
 */
@Component({
  selector: 'app-technician-status-chart',
  imports: [BaseChartDirective],
  templateUrl: './technician-status-chart.html',
})
export class TechnicianStatusChart {
  stats = input.required<TicketStatusStatDto[]>();

  private readonly theme = readChartTheme();

  // statusColorCode is the status' display order in the workflow
  readonly rows = computed(() =>
    [...this.stats()]
      .sort((a, b) => a.statusColorCode - b.statusColorCode)
      .map((stat) => ({
        label: StatusEnum[stat.statusCode as unknown as keyof typeof StatusEnum] ?? String(stat.statusCode),
        count: stat.numberOfTickets,
      })),
  );

  readonly chartData = computed<ChartData<'bar'>>(() => ({
    // The count rides the label: the value stays readable without hovering
    labels: this.rows().map((row) => `${row.label} (${row.count})`),
    datasets: [
      {
        label: 'Tickets',
        data: this.rows().map((row) => row.count),
        backgroundColor: this.theme.series[0],
        maxBarThickness: 24,
        borderRadius: 4,
        borderSkipped: 'start',
      },
    ],
  }));

  readonly chartOptions: ChartConfiguration<'bar'>['options'] = {
    indexAxis: 'y',
    responsive: true,
    maintainAspectRatio: false,
    plugins: { legend: { display: false } },
    scales: {
      x: {
        beginAtZero: true,
        grid: { color: this.theme.grid },
        border: { display: false },
        ticks: { color: this.theme.text, precision: 0 },
      },
      y: {
        grid: { display: false },
        ticks: { color: this.theme.text },
      },
    },
  };
}
