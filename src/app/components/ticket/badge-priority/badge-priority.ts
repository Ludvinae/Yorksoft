import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-badge-priority',
  imports: [],
  templateUrl: './badge-priority.html',
  styleUrl: './badge-priority.css',
})
export class BadgePriority implements OnInit {

  ngOnInit(): void {
    this.label = this.getLabel();
    this.color = this.getColor();
  }

  @Input() priority: number = 0;

  label: string = "";
  color: string = "";

  getLabel(): string {
    if (this.priority <= 25) return 'Basse';
    if (this.priority <= 50) return 'Moyenne';
    if (this.priority <= 75) return 'Elevée';
    else return 'Critique';
  }

  getColor(): string {
    if (this.priority <= 25) return 'badge-info';
    if (this.priority <= 50) return 'badge-success';
    if (this.priority <= 75) return 'badge-warning';
    else return 'badge-error';
  }
}
