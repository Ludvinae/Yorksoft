import { Component, computed, input } from '@angular/core';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartData } from 'chart.js';

@Component({
  selector: 'app-ticket-theme-chart',
  imports: [BaseChartDirective],
  templateUrl: './ticket-theme-chart.html',
  styleUrl: './ticket-theme-chart.css',
})
export class TicketThemeChart {
  themeStats = input.required<TicketThemeStatDto[]>();

  readonly chartType = 'bar';

  chartData = computed<ChartData<'bar'>>(() => ({
    labels: this.themeStats().map((theme) => theme.themeName),

    datasets: [
      {
        label: 'Tickets Par thématique',

        data: this.themeStats().map((theme) => theme.numberOfTickets),

        borderWidth: 1,
        borderRadius: 8,
      },
    ],
  }));

  chartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,

    indexAxis: 'y',

    plugins: {
      legend: {
        display: false,
      },
    },

    scales: {
      x: {
        beginAtZero: true,
        ticks: {
          precision: 0,
        },
      },

      y: {
        ticks: {
          autoSkip: false,
        },
      },
    },
  };
}
