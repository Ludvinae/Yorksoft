import { Component, computed, input } from '@angular/core';
import { ChartConfiguration, ChartData } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-technician-workload-chart',
  imports: [BaseChartDirective],
  templateUrl: './technician-workload-chart.html',
  styleUrl: './technician-workload-chart.css',
})
export class TechnicianWorkloadChart {
  workloadStats = input.required<TechnicianWorkloadDto[]>();

  readonly chartType = 'bar';

  chartData = computed<ChartData<'bar'>>(() => ({
    labels: this.workloadStats().map(
      (tech) => tech.technicianFirstName + ' ' + tech.technicianLastName.charAt(0) + '.',
    ),

    datasets: [
      {
        label: 'Tickets assignés',

        data: this.workloadStats().map((tech) => tech.numberOfAssignedTickets),

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
    },
  };
}
