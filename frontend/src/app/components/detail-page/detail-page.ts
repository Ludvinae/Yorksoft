import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-detail-page',
  imports: [RouterLink],
  templateUrl: './detail-page.html',
  styleUrl: './detail-page.css',
})
export class DetailPage {
  @Input() loading = false;
  @Input() error: string | undefined;
  @Input() title: string | undefined;
  @Input() updateRoute: string | undefined;
}
