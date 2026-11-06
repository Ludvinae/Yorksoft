import { Component, computed, input } from '@angular/core';
import { ChartConfiguration, ChartData } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { readChartTheme, STATUS_CRITICAL, STATUS_GOOD } from '../chart-theme';

/**
 * Tickets the technician closed per week or month, stacked by SLA respect (on time / late).
 * Status colors carry a state, so they always come with their label (legend, table).
 */
@Component({
  selector: 'app-technician-sla-chart',
  imports: [BaseChartDirective],
  templateUrl: './technician-sla-chart.html',
})
export class TechnicianSlaChart {
  points = input.required<SlaPoint[]>();
  unit = input<'week' | 'month'>('week');

  private readonly theme = readChartTheme();

  readonly isEmpty = computed(() => this.points().every((p) => !p.onTime && !p.late));

  readonly labels = computed(() => this.points().map((p) => this.formatBucket(p.date)));

  readonly totals = computed(() => {
    const onTime = this.points().reduce((sum, p) => sum + p.onTime, 0);
    const late = this.points().reduce((sum, p) => sum + p.late, 0);
    return { onTime, late, ratio: onTime + late > 0 ? onTime / (onTime + late) : null };
  });

  readonly chartData = computed<ChartData<'bar'>>(() => ({
    labels: this.labels(),
    datasets: [
      {
        label: 'Dans les délais',
        data: this.points().map((p) => p.onTime),
        backgroundColor: STATUS_GOOD,
        maxBarThickness: 24,
        // Rounded data-end only when it is the top of the stack (no late segment above it)
        borderRadius: (ctx) => (this.points()[ctx.dataIndex]?.late ? 0 : 4),
        borderSkipped: 'start',
      },
      {
        label: 'En retard',
        data: this.points().map((p) => p.late),
        backgroundColor: STATUS_CRITICAL,
        maxBarThickness: 24,
        borderRadius: 4,
        borderSkipped: 'start',
        // 2px gap in the surface color between the two stacked segments
        borderWidth: { top: 0, right: 0, bottom: 2, left: 0 },
        borderColor: this.theme.surface,
      },
    ],
  }));

  readonly chartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    interaction: { mode: 'index', intersect: false },
    plugins: {
      legend: { position: 'bottom', labels: { color: this.theme.text } },
      tooltip: { mode: 'index', intersect: false },
    },
    scales: {
      x: {
        stacked: true,
        grid: { display: false },
        ticks: { color: this.theme.text, maxTicksLimit: 8, maxRotation: 0 },
      },
      y: {
        stacked: true,
        beginAtZero: true,
        grid: { color: this.theme.grid },
        border: { display: false },
        ticks: { color: this.theme.text, precision: 0 },
      },
    },
  };

  private formatBucket(isoDate: string): string {
    const [year, month, day] = isoDate.split('-').map(Number);
    const date = new Date(year, month - 1, day);
    return this.unit() === 'month'
      ? date.toLocaleDateString('fr-FR', { month: 'short', year: '2-digit' })
      : `sem. du ${date.toLocaleDateString('fr-FR', { day: 'numeric', month: 'short' })}`;
  }
}
