import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-badge-priority',
  imports: [],
  templateUrl: './badge-priority.html',
  styleUrl: './badge-priority.css',
})
export class BadgePriority {
  @Input() priority: number = 0;

  getLabel(): string {
    if (this.priority <= 25) return "Basse";
    if (this.priority <= 50) return "Moyenne";
    if (this.priority <= 75) return "Elevée";
    else return "Critique";
  }

  getColor(): string {
    if (this.priority <= 25) return 'badge-info';
    if (this.priority <= 50) return 'badge-success';
    if (this.priority <= 75) return 'badge-warning';
    else return 'badge-error';
  }
}
