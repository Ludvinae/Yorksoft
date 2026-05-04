import { Component, inject, OnInit } from '@angular/core';
import {GenericList} from "../../../../components/list/generic-list/generic-list";
import {Title} from "../../../../components/list/title/title";
import { VersionService } from '../../../../services/api/version-service';

@Component({
  selector: 'app-version-list',
  imports: [GenericList, Title],
  templateUrl: './version-list.html',
  styleUrl: './version-list.css',
})
export class VersionList implements OnInit {
  versionService = inject(VersionService);

  versionList = this.versionService.versionListDto;

  ngOnInit() {
    this.versionService.getAllDto().subscribe();
  }

  onDelete(id: number) {
    this.versionService.delete(id).subscribe({
      next: () => {
        alert('Supprimé');
        this.versionService.getAllDto().subscribe();
      },
      error: () => alert('Erreur'),
    });
  }
}
