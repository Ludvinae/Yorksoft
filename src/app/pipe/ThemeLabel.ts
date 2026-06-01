import { Pipe, PipeTransform } from '@angular/core';
import { ThemeEnum } from '../models/enum/ThemeEnum';

@Pipe({
  name: 'themeLabel',
})
export class ThemeLabel implements PipeTransform {
  transform(theme: string): string {
    return ThemeEnum[theme as keyof typeof ThemeEnum] ?? theme;
  }
}
