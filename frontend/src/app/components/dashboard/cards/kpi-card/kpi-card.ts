import { Component, EventEmitter, Input, Output } from '@angular/core';

/**
 * Presentation-only KPI tile: the page using it decides the color (colorClass) and what a click does (cardClick).
 */
@Component({
  selector: 'app-kpi-card',
  imports: [],
  templateUrl: './kpi-card.html',
  styleUrl: './kpi-card.css',
})
export class KpiCard {
  @Input({ required: true }) title!: string;
  /** Shown as "-" when null (e.g. no data yet). */
  @Input({ required: true }) value!: string | number | null;
  /** Unit appended to the value (e.g. "m"), not shown when value is null. */
  @Input() suffix = '';
  @Input() icon?: string;
  @Input() colorClass = '';

  /** The card is clickable (hover styles, pointer) only when the parent listens to this output. */
  @Output() cardClick = new EventEmitter<void>();

  get clickable(): boolean {
    return this.cardClick.observed;
  }

  onClick(): void {
    if (this.clickable) this.cardClick.emit();
  }
}
