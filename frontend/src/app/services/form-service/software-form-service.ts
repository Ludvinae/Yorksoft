import { inject, Injectable } from '@angular/core';
import { SoftwareService } from '../api/software-service';

@Injectable({
  providedIn: 'root',
})
export class SoftwareFormService {
  private softwareService = inject(SoftwareService);

  load(id: number) {
    return this.softwareService.get(id);
  }

  mapToForm(software: Software) {
    return {
      name: software.name ?? '',
      description: software.description ?? '',
      idSoftwareType: software.idSoftwareType ?? 0,
    };
  }

  mapToDto(formValue: SoftwareForm, existing?: Software): Software {
    return {
      ...formValue,
      idSoftware: existing?.idSoftware ?? 0,
    };
  }

  save(dto: Software, isEdit: boolean) {
    return isEdit
      ? this.softwareService.update(dto, dto.idSoftware)
      : this.softwareService.create(dto);
  }
}
