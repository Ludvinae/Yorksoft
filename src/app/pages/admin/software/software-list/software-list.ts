import { Component, inject } from '@angular/core';
import { SoftwareService } from '../../../../services/software-service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-software-list',
  imports: [RouterLink],
  templateUrl: './software-list.html',
  styleUrl: './software-list.css',
})
export class SoftwareList {
  softwareService = inject(SoftwareService);

  softwareList = this.softwareService.softwareList;

  ngOnInit() {
    this.softwareService.getAll().subscribe();
  }

  delete(id: number) {
    if (confirm('Supprimer ?')) {
      this.softwareService.delete(id).subscribe();
    }
  }
}
