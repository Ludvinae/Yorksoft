import { Component, inject, OnInit } from '@angular/core';
import {GenericList} from "../../../../components/list/generic-list/generic-list";
import {Title} from "../../../../components/list/title/title";
import { ImpactService } from '../../../../services/api/impact-service';

@Component({
  selector: 'app-impact-list',
  imports: [GenericList, Title],
  templateUrl: './impact-list.html',
  styleUrl: './impact-list.css',
})
export class ImpactList implements OnInit {
  impactService = inject(ImpactService);

  impactList = this.impactService.impactList;

  ngOnInit() {
    this.impactService.getAll().subscribe();
  }

  onDelete(id: number) {
    this.impactService.delete(id).subscribe({
      next: () => {
        alert('Supprimé');
        this.impactService.getAll().subscribe();
      },
      error: () => alert('Erreur'),
    });
  }
}
