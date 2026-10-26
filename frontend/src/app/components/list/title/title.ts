import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { PageHeader } from '../../layout/page-header/page-header';

@Component({
  selector: 'app-title',
  imports: [RouterLink, PageHeader],
  templateUrl: './title.html',
  styleUrl: './title.css',
})
export class Title {
  @Input() title!: string;
  @Input() route!: string;

  get createRoute(): string {
    return this.route == 'ticket' ? `/${this.route}/create` : `/admin/${this.route}/create`;
  }
}
