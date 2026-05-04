import { Component, inject } from '@angular/core';
import {GenericList} from "../../../../components/list/generic-list/generic-list";
import {Title} from "../../../../components/list/title/title";
import { ThemeService } from '../../../../services/api/theme-service';

@Component({
  selector: 'app-theme-list',
  imports: [GenericList, Title],
  templateUrl: './theme-list.html',
  styleUrl: './theme-list.css',
})
export class ThemeList {
  themeService = inject(ThemeService);

  themeList = this.themeService.themeList;

  ngOnInit() {
    this.themeService.getAll().subscribe();
  }

  onDelete(id: number) {
    this.themeService.delete(id).subscribe({
      next: () => {
        alert('Supprimé');
        this.themeService.getAll().subscribe();
      },
      error: () => alert('Erreur'),
    });
  }
}
