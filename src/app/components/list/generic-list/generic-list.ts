import { Component, inject, Input } from '@angular/core';
import { BreakpointSwitch } from '../../../services/breakpoint-switch';
import { DesktopTable } from '../desktop-table/desktop-table';

type ColumnConfig<T> = {
  label: string;
  field: keyof T;
}

@Component({
  selector: 'app-generic-list',
  imports: [DesktopTable],
  templateUrl: './generic-list.html',
  styleUrl: './generic-list.css',
})
export class GenericList<T> {
  @Input() data!: T[];
  @Input() columns!: ColumnConfig<T>[];

  isMobile = inject(BreakpointSwitch).isMobile; // ton service
}
