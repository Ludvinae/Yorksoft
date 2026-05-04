import { Component, inject, OnInit } from '@angular/core';
import {GenericList} from "../../../../components/list/generic-list/generic-list";
import {Title} from "../../../../components/list/title/title";
import { VersionTypeService } from '../../../../services/api/version-type-service';

@Component({
  selector: 'app-version-type-list',
  imports: [GenericList, Title],
  templateUrl: './version-type-list.html',
  styleUrl: './version-type-list.css',
})
export class VersionTypeList implements OnInit {
  versionTypeService = inject(VersionTypeService);

  versionTypeList = this.versionTypeService.versionTypeList;

  ngOnInit() {
    this.versionTypeService.getAll().subscribe();
  }

  onDelete(id: number) {
    this.versionTypeService.delete(id).subscribe({
      next: () => {
        alert('Supprimé');
        this.versionTypeService.getAll().subscribe();
      },
      error: () => alert('Erreur'),
    });
  }
}
