import { Component, computed, input } from '@angular/core';
import { ChartConfiguration, ChartData, Plugin } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { readChartTheme } from '../chart-theme';

// Vertical hairline under the pointer, so the reader aims at a date rather than at a 2px line
const crosshair: Plugin<'line'> = {
  id: 'crosshair',
  afterDatasetsDraw(chart) {
    const active = chart.tooltip?.getActiveElements();
    if (!active?.length) return;
    const x = active[0].element.x;
    const { top, bottom } = chart.chartArea;
    const ctx = chart.ctx;
    ctx.save();
    ctx.beginPath();
    ctx.moveTo(x, top);
    ctx.lineTo(x, bottom);
    ctx.lineWidth = 1;
    ctx.strokeStyle = readChartTheme().grid;
    ctx.stroke();
    ctx.restore();
  },
};

/**
 * Technician's tickets taken in charge and closed over the selected period, per day or per week.
 */
@Component({
  selector: 'app-technician-evolution-chart',
  imports: [BaseChartDirective],
  templateUrl: './technician-evolution-chart.html',
})
export class TechnicianEvolutionChart {
  points = input.required<TicketEvolutionPoint[]>();
  unit = input<'day' | 'week'>('day');

  private readonly theme = readChartTheme();
  readonly plugins = [crosshair];

  readonly isEmpty = computed(() => this.points().every((p) => !p.takenInCharge && !p.closed));

  readonly labels = computed(() => this.points().map((p) => this.formatBucket(p.date)));

  readonly chartData = computed<ChartData<'line'>>(() => {
    const [taken, closed] = this.theme.series;
    return {
      labels: this.labels(),
      datasets: [
        this.dataset('Pris en charge', this.points().map((p) => p.takenInCharge), taken),
        this.dataset('Clos', this.points().map((p) => p.closed), closed),
      ],
    };
  });

  readonly chartOptions: ChartConfiguration<'line'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    interaction: { mode: 'index', intersect: false },
    plugins: {
      legend: {
        position: 'bottom',
        labels: { color: this.theme.text, usePointStyle: true, pointStyle: 'line' },
      },
      tooltip: { mode: 'index', intersect: false, usePointStyle: true },
    },
    scales: {
      x: {
        grid: { display: false },
        ticks: { color: this.theme.text, maxTicksLimit: 8, maxRotation: 0 },
      },
      y: {
        beginAtZero: true,
        grid: { color: this.theme.grid },
        border: { display: false },
        ticks: { color: this.theme.text, precision: 0 },
      },
    },
  };

  private dataset(label: string, data: number[], color: string) {
    return {
      label,
      data,
      borderColor: color,
      backgroundColor: color,
      borderWidth: 2,
      borderCapStyle: 'round' as const,
      borderJoinStyle: 'round' as const,
      pointRadius: 0,
      pointHoverRadius: 5,
      pointHoverBorderWidth: 2,
      pointHoverBorderColor: this.theme.surface,
      tension: 0,
    };
  }

  private formatBucket(isoDate: string): string {
    const [year, month, day] = isoDate.split('-').map(Number);
    const label = new Date(year, month - 1, day).toLocaleDateString('fr-FR', { day: 'numeric', month: 'short' });
    return this.unit() === 'week' ? `sem. du ${label}` : label;
  }
}
