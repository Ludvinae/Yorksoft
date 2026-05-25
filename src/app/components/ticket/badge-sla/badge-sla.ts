import { Component, Input } from '@angular/core';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-badge-sla',
  imports: [DatePipe],
  templateUrl: './badge-sla.html',
  styleUrl: './badge-sla.css',
})
export class BadgeSla {
  @Input()
  overdue!: boolean;

  @Input()
  predictedDeadline!: string | Date | null;

  @Input()
  priority!: number;

  isNearDeadline(): boolean {
    if (!this.predictedDeadline || this.overdue) {
      return false;
    }

    const deadline = new Date(this.predictedDeadline).getTime();

    const remainingMs = deadline - Date.now();

    const warningThreshold = this.resolveThreshold();

    return remainingMs <= warningThreshold;
  }

  private resolveThreshold(): number {
    if (this.priority >= 90) {
      return 30 * 60 * 1000; // 30mn restantes
    }

    if (this.priority >= 70) {
      return 2 * 60 * 60 * 1000; // 2h restantes
    }

    return 8 * 60 * 60 * 1000; // 8h restantes
  }

  remainingTime(): string {
    if (!this.predictedDeadline) {
      return '';
    }

    const remainingMs = new Date(this.predictedDeadline).getTime() - Date.now();

    if (remainingMs <= 0) {
      return 'Dépassé';
    }

    const hours = Math.floor(remainingMs / (1000 * 60 * 60));

    const minutes = Math.floor((remainingMs % (1000 * 60 * 60)) / (1000 * 60));

    if (hours > 0) {
      return `${hours}h ${minutes}m`;
    }

    return `${minutes} min`;
  }
}
