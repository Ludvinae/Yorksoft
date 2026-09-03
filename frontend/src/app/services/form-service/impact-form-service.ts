import { inject, Injectable } from '@angular/core';
import { ImpactService } from '../api/impact-service';

@Injectable({
  providedIn: 'root',
})
export class ImpactFormService {
  private impactService = inject(ImpactService);

  load(id: number) {
    return this.impactService.get(id);
  }

  mapToForm(impact: Impact) {
    return {
      designation: impact.designation ?? '',
      priorityFactor: impact.priorityFactor,
      description: impact.description ?? '',
    };
  }

  mapToDto(formValue: ImpactForm, existing?: Impact): Impact {
    return {
      ...formValue,
      idImpact: existing?.idImpact ?? 0,
    };
  }

  save(dto: Impact, isEdit: boolean) {
    return isEdit ? this.impactService.update(dto, dto.idImpact) : this.impactService.create(dto);
  }
}
