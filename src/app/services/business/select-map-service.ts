import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SelectMapService {
  mapToOptions<T>(data: T[], label: (item: T) => string, value: (item: T) => any) {
    return data.map((item) => ({
      labelKey: label(item),
      valueKey: value(item),
    }));
  }
}
