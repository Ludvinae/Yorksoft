import { Component, Input, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-title',
  imports: [RouterLink],
  templateUrl: './title.html',
  styleUrl: './title.css',
})
export class Title {
  @Input() title!: string;
  @Input() route!: string;

  get createRoute(): string {
    return `/admin/${this.route}/create`;
  }
}
