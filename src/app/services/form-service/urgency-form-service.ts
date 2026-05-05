import { inject, Injectable } from '@angular/core';
import { UrgencyService } from '../api/urgency-service';

@Injectable({
  providedIn: 'root',
})
export class UrgencyFormService {
  private urgencyService = inject(UrgencyService);

  load(id: number) {
    return this.urgencyService.get(id);
  }

  mapToForm(urgency: Urgency) {
    return {
      designation: urgency.designation,
      description: urgency.description,
    };
  }

  mapToDto(formValue: UrgencyForm, existing?: Urgency): Urgency {
    return {
      ...formValue,
      idUrgency: existing?.idUrgency ?? 0,
    };
  }

  save(dto: Urgency, isEdit: boolean) {
    return isEdit
      ? this.urgencyService.update(dto, dto.idUrgency)
      : this.urgencyService.create(dto);
  }

}
