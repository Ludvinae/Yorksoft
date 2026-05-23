import { inject, Injectable } from '@angular/core';
import { StatusService } from '../api/status-service';

@Injectable({
  providedIn: 'root',
})
export class StatusFormService {
  private statusService = inject(StatusService);

  load(id: number) {
    return this.statusService.get(id);
  }

  mapToForm(status: Status) {
    return {
      designation: status.designation ?? '',
      code: status.code,
      displayOrder: status.displayOrder,
    };
  }

  mapToDto(formValue: StatusForm, existing?: Status): Status {
    return {
      ...formValue,
      idStatus: existing?.idStatus ?? 0,
    };
  }

  save(dto: Status, isEdit: boolean) {
    return isEdit ? this.statusService.update(dto, dto.idStatus) : this.statusService.create(dto);
  }
}
