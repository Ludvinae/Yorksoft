import { Component } from '@angular/core';
import { DesktopTableRow } from '../desktop-table-row/desktop-table-row';

@Component({
  selector: 'app-desktop-table',
  imports: [DesktopTableRow],
  templateUrl: './desktop-table.html',
  styleUrl: './desktop-table.css',
})
export class DesktopTable {}
