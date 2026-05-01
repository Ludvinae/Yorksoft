import { Component, inject } from '@angular/core';
import { SoftwareService } from '../../services/api/software-service';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-new-ticket',
  imports: [],
  templateUrl: './new-ticket.html',
  styleUrl: './new-ticket.css',
})
export class NewTicket {
  softwareService = inject(SoftwareService);
  //selectMapper = inject(SelectMapService)

  softwareSignal = toSignal(this.softwareService.getAll(), { initialValue: [] });
}
