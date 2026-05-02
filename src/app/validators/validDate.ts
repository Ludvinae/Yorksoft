import { AbstractControl } from '@angular/forms';

export function validDate(control: AbstractControl) {
  const value = control.value;
  if (!value) return null;

  const date = new Date(value);
  return isNaN(date.getTime()) ? { invalidDate: true } : null;
}
