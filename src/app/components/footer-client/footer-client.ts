import { Component, inject } from '@angular/core';
import { ClientStoreService } from '../../services/client-store-service';

@Component({
  selector: 'app-footer-client',
  imports: [],
  templateUrl: './footer-client.html',
  styleUrl: './footer-client.css',
})
export class FooterClient {
  protected readonly store = inject(ClientStoreService);

  nextPage(): void {
    this.store.nextPage();
  }

  previousPage(): void {
    this.store.previousPage();
  }
}
