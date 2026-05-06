import { inject, Injectable } from '@angular/core';
import { VersionService } from '../api/version-service';

@Injectable({
  providedIn: 'root',
})
export class VersionFormService {
  private versionService = inject(VersionService);

  load(id: number) {
    return this.versionService.get(id);
  }

  mapToForm(version: Version) {
    return {
      versionNumber: version.versionNumber ?? '',
      publicationDate: version.publicationDate ?
        this.formatDate(version.publicationDate) : '',
      idVersionType: version.idVersionType ?? 0,
      idSoftware: version.idSoftware ?? 0,
    };
  }

  mapToDto(formValue: VersionForm, existing?: Version): Version {
    return {
      ...formValue,
      idVersion: existing?.idVersion ?? 0,
      publicationDate: new Date(formValue.publicationDate),
    };
  }

  save(dto: Version, isEdit: boolean) {
    return isEdit
      ? this.versionService.update(dto, dto.idVersion)
      : this.versionService.create(dto);
  }

  private formatDate(date: string | Date): string {
    return new Date(date).toISOString().split('T')[0];
  }
}
