import { Component, EventEmitter, inject, Input, Output, TemplateRef } from '@angular/core';
import { BreakpointSwitch } from '../../../services/business/breakpoint-switch';
import { DesktopTable } from '../desktop-table/desktop-table';
import { MobileCardList } from '../mobile-card-list/mobile-card-list';

@Component({
  selector: 'app-generic-list',
  imports: [DesktopTable, MobileCardList],
  templateUrl: './generic-list.html',
  styleUrl: './generic-list.css',
})
export class GenericList<T> {
  private displayService = inject(BreakpointSwitch);

  @Input({ required: true }) data!: T[];
  @Input({ required: true }) columns!: ColumnConfig<T>[];
  @Input({ required: true }) itemTemplate!: TemplateRef<any>;
  @Input({ required: true }) idField!: keyof T;

  // pour générer les liens automatiquement
  @Input({ required: true }) baseRoute!: string;

  @Output() deleteItem = new EventEmitter<number>();

  isMobile = this.displayService.isMobile;
}
