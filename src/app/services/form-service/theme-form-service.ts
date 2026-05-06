import { inject, Injectable } from '@angular/core';
import { ThemeService } from '../api/theme-service';

@Injectable({
  providedIn: 'root',
})
export class ThemeFormService {
  private themeService = inject(ThemeService);

  load(id: number) {
    return this.themeService.get(id);
  }

  mapToForm(theme: Theme) {
    return {
      designation: theme.designation ?? "",
      description: theme.description ?? "",
    };
  }

  mapToDto(formValue: ThemeForm, existing?: Theme): Theme {
    return {
      ...formValue,
      idTheme: existing?.idTheme ?? 0,
    };
  }

  save(dto: Theme, isEdit: boolean) {
    return isEdit
      ? this.themeService.update(dto, dto.idTheme)
      : this.themeService.create(dto);
  }

}
