import { Component, Input } from '@angular/core';
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

  createRoute: string = "/admin" + this.route + "/create";
}
