import { inject, Injectable } from '@angular/core';
import { SoftwareTypeService } from '../api/software-type-service';

@Injectable({
  providedIn: 'root',
})
export class SoftwareTypeFormService {
  private softwareTypeService = inject(SoftwareTypeService);

  load(id: number) {
    return this.softwareTypeService.get(id);
  }

  mapToForm(softwareType: SoftwareType) {
    return {
      designation: softwareType.designation ?? "",
    };
  }

  mapToDto(formValue: SoftwareTypeForm, existing?: SoftwareType): SoftwareType {
    return {
      ...formValue,
      idSoftwareType: existing?.idSoftwareType ?? 0,
    };
  }

  save(dto: SoftwareType, isEdit: boolean) {
    return isEdit
      ? this.softwareTypeService.update(dto, dto.idSoftwareType)
      : this.softwareTypeService.create(dto);
  }

}
