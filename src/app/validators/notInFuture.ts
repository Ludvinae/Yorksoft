import { AbstractControl } from '@angular/forms';

export function notInFuture(control: AbstractControl) {
  const value = control.value;
  if (!value) return null;

  const date = new Date(value);
  const today = new Date();

  return date > today ? { futureDate: true } : null;
}
