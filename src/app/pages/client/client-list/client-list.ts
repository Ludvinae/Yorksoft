import { Component, inject, OnInit } from '@angular/core';
import { ClientStoreService } from '../../../services/client-store-service';
import { ClientService } from '../../../services/api/client-service';
import { FooterClient } from '../../../components/footer-client/footer-client';
import {TitleClient} from '../../../components/title-bar/title-client/title-client';

@Component({
  selector: 'app-client-list',
  imports: [FooterClient, TitleClient],
  templateUrl: './client-list.html',
  styleUrl: './client-list.css',
})
export class ClientList implements OnInit {
  clientService = inject(ClientService);
  storeService = inject(ClientStoreService);

  ngOnInit() {
    this.storeService.loadClients();
  }

  onDelete(id: number) {
    this.clientService.delete(id).subscribe({
      next: () => {
        alert('Supprimé');
        this.storeService.loadClients();
      },
      error: () => alert('Erreur'),
    });
  }

  isSorted(field: string): boolean {
    return this.storeService.sortField() === field;
  }

  isAsc(): boolean {
    return this.storeService.sortDirection() === 'asc';
  }
}
