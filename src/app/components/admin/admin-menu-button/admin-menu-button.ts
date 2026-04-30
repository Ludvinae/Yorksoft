import { Component, Input } from '@angular/core';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-admin-menu-button',
  imports: [RouterLink],
  templateUrl: './admin-menu-button.html',
  styleUrl: './admin-menu-button.css',
})
export class AdminMenuButton {
  @Input({ required: true }) label!: string;
  @Input({ required: true }) link!: string;
}
