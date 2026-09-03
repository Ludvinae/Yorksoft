import { Component, inject, OnInit } from '@angular/core';
import { GenericList } from '../../../../components/list/generic-list/generic-list';
import { Title } from '../../../../components/list/title/title';
import { UrgencyService } from '../../../../services/api/urgency-service';

@Component({
  selector: 'app-urgency-list',
  imports: [GenericList, Title],
  templateUrl: './urgency-list.html',
  styleUrl: './urgency-list.css',
})
export class UrgencyList implements OnInit {
  urgencyService = inject(UrgencyService);

  urgencyList = this.urgencyService.urgencyList;

  ngOnInit() {
    this.urgencyService.getAll().subscribe();
  }

  onDelete(id: number) {
    this.urgencyService.delete(id).subscribe({
      next: () => {
        alert('Supprimé');
        this.urgencyService.getAll().subscribe();
      },
      error: () => alert('Erreur'),
    });
  }
}
