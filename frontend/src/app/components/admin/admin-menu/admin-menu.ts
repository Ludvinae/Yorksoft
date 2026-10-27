import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';

export type AdminMenuItems = {
  label: string;
  link: string;
};

export type AdminMenuGroup = {
  title: string;
  items: AdminMenuItems[];
};

/** Admin entry points, one titled column of stacked buttons per group (same look as the theme picker in settings). */
@Component({
  selector: 'app-admin-menu',
  imports: [RouterLink],
  templateUrl: './admin-menu.html',
  styleUrl: './admin-menu.css',
})
export class AdminMenu {
  @Input({ required: true }) groups!: AdminMenuGroup[];
}
