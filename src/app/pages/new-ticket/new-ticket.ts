import { Component, inject } from '@angular/core';
import { FormSet } from '../../components/forms/form-set/form-set';
import { FieldSelect } from '../../components/forms/field-select/field-select';
import { SoftwareService } from '../../services/software-service';
import { toSignal } from '@angular/core/rxjs-interop';
import { map } from 'rxjs';
import { SelectMapService } from '../../services/select-map-service';

@Component({
  selector: 'app-new-ticket',
  imports: [],
  templateUrl: './new-ticket.html',
  styleUrl: './new-ticket.css',
})
export class NewTicket {
  softwareService = inject(SoftwareService);
  //selectMapper = inject(SelectMapService)

  softwareSignal = toSignal(this.softwareService.getSoftware(), { initialValue: [] });

}
