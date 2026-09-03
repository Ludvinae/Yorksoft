import { AbstractControl } from '@angular/forms';

export function notInPast(control: AbstractControl) {
  const value = control.value;
  if (!value) return null;

  const date = new Date(value);
  const today = new Date();

  return date < today ? { pastDate: true } : null;
}
