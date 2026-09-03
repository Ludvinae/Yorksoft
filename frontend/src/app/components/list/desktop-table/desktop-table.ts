import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-desktop-table',
  imports: [RouterLink],
  templateUrl: './desktop-table.html',
  styleUrl: './desktop-table.css',
})
export class DesktopTable<T> {
  @Input({ required: true }) data!: T[];
  @Input({ required: true }) columns!: ColumnConfig<T>[];
  @Input({ required: true }) baseRoute!: string;
  // Adapte le champ ID avec le nom correspondant
  @Input({ required: true }) idField!: keyof T;

  @Output() deleteItem = new EventEmitter<number>();

  getId(item: T): any {
    return (item as any)[this.idField];
  }

  getValue(item: T, field: keyof T): any {
    return item[field];
  }

  delete(id: number) {
    if (confirm('Supprimer ?')) {
      this.deleteItem.emit(id);
    }
  }
}
