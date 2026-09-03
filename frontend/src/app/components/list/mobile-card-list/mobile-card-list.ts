import { Component, EventEmitter, Input, Output, TemplateRef } from '@angular/core';
import { NgTemplateOutlet } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-mobile-card-list',
  imports: [NgTemplateOutlet, RouterLink],
  templateUrl: './mobile-card-list.html',
  styleUrl: './mobile-card-list.css',
})
export class MobileCardList<T> {
  @Input({ required: true }) data!: T[];
  @Input({ required: true }) itemTemplate!: TemplateRef<any>;
  @Input({ required: true }) baseRoute!: string;
  // Adapte le champ ID avec le nom correspondant
  @Input({ required: true }) idField!: keyof T;

  @Output() deleteItem = new EventEmitter<number>();

  getId(item: T): any {
    return (item as any)[this.idField];
  }

  delete(id: number) {
    if (confirm('Supprimer ?')) {
      this.deleteItem.emit(id);
    }
  }
}
