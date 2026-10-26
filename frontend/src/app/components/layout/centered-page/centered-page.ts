import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-centered-page',
  imports: [],
  templateUrl: './centered-page.html',
  styleUrl: './centered-page.css',
})
export class CenteredPage {
  @Input() title!: string;
  @Input() subtitle?: string;
}
