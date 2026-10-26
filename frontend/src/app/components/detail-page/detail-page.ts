import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { PageHeader } from '../layout/page-header/page-header';

@Component({
  selector: 'app-detail-page',
  imports: [RouterLink, PageHeader],
  templateUrl: './detail-page.html',
  styleUrl: './detail-page.css',
})
export class DetailPage {
  @Input() loading = false;
  @Input() error: string | undefined;
  @Input() title: string | undefined;
  @Input() updateRoute: string | undefined;
}
