import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PageHeader } from '../../layout/page-header/page-header';

@Component({
  selector: 'app-title-bar',
  imports: [FormsModule, PageHeader],
  templateUrl: './title-bar.html',
  styleUrl: './title-bar.css',
})
export class TitleBar {
  @Input() title!: string;
  @Input() searchPlaceholder = 'Rechercher...';

  @Output() search = new EventEmitter<string>();

  searchValue = '';
  private searchTimeout: any;

  // Fonction de recherche, avec debounce 300ms
  onSearch(value: string): void {
    this.searchValue = value;
    clearTimeout(this.searchTimeout);
    this.searchTimeout = setTimeout(() => {
      this.search.emit(value);
    }, 300);
  }
}
