import { Component, inject } from '@angular/core';
import {GenericList} from "../../../../components/list/generic-list/generic-list";
import {Title} from "../../../../components/list/title/title";
import { StatusService } from '../../../../services/api/status-service';

@Component({
  selector: 'app-status-list',
  imports: [GenericList, Title],
  templateUrl: './status-list.html',
  styleUrl: './status-list.css',
})
export class StatusList {
  statusService = inject(StatusService);

  statusList = this.statusService.statusList;

  ngOnInit() {
    this.statusService.getAll().subscribe();
  }

  onDelete(id: number) {
    this.statusService.delete(id).subscribe({
      next: () => {
        alert('Supprimé');
        this.statusService.getAll().subscribe();
      },
      error: () => alert('Erreur'),
    });
  }
}
