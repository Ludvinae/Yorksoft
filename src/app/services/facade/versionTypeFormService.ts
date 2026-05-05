import { inject, Injectable } from '@angular/core';
import { VersionTypeService } from '../api/version-type-service';
import { VersionTypeForm } from '../../models/form-group/VersionTypeForm';
import { ɵFormGroupRawValue, ɵTypedOrUntyped } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})
export class VersionTypeFormService {
  private versionTypeService = inject(VersionTypeService);

  load(id: number) {
    return this.versionTypeService.get(id);
  }

  mapToForm(type: VersionType) {
    return {
      designation: type.designation,
      urgencyMalus: type.urgencyMalus,
    };
  }

  mapToDto(
    formValue: ɵTypedOrUntyped<VersionTypeForm, ɵFormGroupRawValue<VersionTypeForm>, any>,
    existing?: VersionType | undefined,
  ): VersionType {
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

  private formatDate(date: string | Date): string {
    return new Date(date).toISOString().split('T')[0];
  }
}
