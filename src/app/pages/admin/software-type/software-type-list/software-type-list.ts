import { Component, inject, OnInit } from '@angular/core';
import { GenericList } from '../../../../components/list/generic-list/generic-list';
import { SoftwareTypeService } from '../../../../services/software-type-service';
import { Title } from '../../../../components/list/title/title';

@Component({
  selector: 'app-software-type-list',
  imports: [GenericList, Title],
  templateUrl: './software-type-list.html',
  styleUrl: './software-type-list.css',
})
export class SoftwareTypeList implements OnInit {
  softwareTypeService = inject(SoftwareTypeService);

  softwareTypeList = this.softwareTypeService.softwareTypeList;

  ngOnInit() {
    this.softwareTypeService.getAll().subscribe();
  }

  onDelete(id: number) {
    this.softwareTypeService.delete(id).subscribe({
      next: () => {
        alert('Supprimé');
        this.softwareTypeService.getAll().subscribe();
      },
      error: () => alert('Erreur'),
    });
  }
}
