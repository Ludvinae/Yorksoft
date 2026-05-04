import { Component, inject, OnInit } from '@angular/core';
import { SoftwareService } from '../../../../services/api/software-service';
import {GenericList} from '../../../../components/list/generic-list/generic-list';
import {Title} from '../../../../components/list/title/title';

@Component({
  selector: 'app-software-list',
  imports: [GenericList, Title],
  templateUrl: './software-list.html',
  styleUrl: './software-list.css',
})
export class SoftwareList implements OnInit {
  softwareService = inject(SoftwareService);

  softwareList = this.softwareService.softwareListDetail;

  ngOnInit() {
    this.softwareService.getAllDetail().subscribe();
  }

  onDelete(id: number) {
    this.softwareService.delete(id).subscribe({
      next: () => {
        alert('Supprimé');
        this.softwareService.getAllDetail().subscribe();
      },
      error: () => alert('Erreur'),
    });
  }
}
