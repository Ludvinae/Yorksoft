import { inject, Injectable } from '@angular/core';
import { VersionTypeService } from '../api/version-type-service';

@Injectable({
  providedIn: 'root',
})
export class VersionTypeFormService {
  private versionTypeService = inject(VersionTypeService);

  load(id: number) {
    return this.versionTypeService.get(id);
  }

  mapToForm(version: VersionType) {
    return {
      designation: version.designation,
      urgencyMalus: version.urgencyMalus,
    };
  }

  mapToDto(formValue: VersionTypeForm, existing?: VersionType): VersionType {
    return {
      ...formValue,
      idVersionType: existing?.idVersionType ?? 0,
    };
  }

  save(dto: VersionType, isEdit: boolean) {
    return isEdit
      ? this.versionTypeService.update(dto, dto.idVersionType)
      : this.versionTypeService.create(dto);
  }
}
