import { Component, computed, input } from '@angular/core';
import { ChartConfiguration, ChartData } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-ticket-status-pie-chart',
  imports: [BaseChartDirective],
  templateUrl: './ticket-status-pie-chart.html',
  styleUrl: './ticket-status-pie-chart.css',
})
export class TicketStatusPieChart {
  stats = input.required<TicketStatusStatDto[]>();

  chartType: 'doughnut' = 'doughnut';

  getColor(colorCode: number): string {
    switch (colorCode) {
      case 1:
        return 'red';
      case 2:
        return 'green';
      case 3:
        return 'yellow';
      case 4:
        return 'blue';
      case 5:
        return 'purple';
      case 6:
        return 'pink';
      case 7:
        return 'orange';
      default:
        return 'black';
    }
  }

  chartData = computed<ChartData<'doughnut'>>(() => ({
    labels: this.stats().map((stat) => stat.statusCode),

    datasets: [
      {
        data: this.stats().map((stat) => stat.numberOfTickets),

        backgroundColor: this.stats().map((stat) => this.getColor(stat.statusColorCode)),

        borderWidth: 2,
      },
    ],
  }));

  chartOptions: ChartConfiguration<'doughnut'>['options'] = {
    responsive: true,

    plugins: {
      legend: {
        position: 'bottom',
      },
    },
  };
}
