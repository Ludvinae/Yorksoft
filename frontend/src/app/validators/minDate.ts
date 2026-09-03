import { AbstractControl } from '@angular/forms';

export function minDate(min: Date) {
  return (control: AbstractControl) => {
    const value = control.value;
    if (!value) return null;

    const date = new Date(value);
    return date < min ? { minDate: true } : null;
  };
}
