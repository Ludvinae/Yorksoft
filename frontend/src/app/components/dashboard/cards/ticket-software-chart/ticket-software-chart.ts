import { Component, computed, input } from '@angular/core';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartData } from 'chart.js';

@Component({
  selector: 'app-ticket-software-chart',
  imports: [BaseChartDirective],
  templateUrl: './ticket-software-chart.html',
  styleUrl: './ticket-software-chart.css',
})
export class TicketSoftwareChart {
  softwareStats = input.required<TicketSoftwareStatDto[]>();

  readonly chartType = 'bar';

  chartData = computed<ChartData<'bar'>>(() => ({
    labels: this.softwareStats().map((soft) => soft.softwareName),

    datasets: [
      {
        label: 'Tickets par logiciel',

        data: this.softwareStats().map((soft) => soft.numberOfTickets),

        borderWidth: 1,
        borderRadius: 8,
      },
    ],
  }));

  chartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,

    indexAxis: 'x',

    plugins: {
      legend: {
        display: false,
      },
    },

    scales: {
      y: {
        beginAtZero: true,
        ticks: {
          precision: 0,
        },
      },
    },
  };
}
