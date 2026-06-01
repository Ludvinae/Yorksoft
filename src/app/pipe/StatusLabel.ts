import { Pipe, PipeTransform } from '@angular/core';
import { StatusEnum } from '../models/enum/StatusEnum';

@Pipe({
  name: 'statusLabel',
})
export class StatusLabel implements PipeTransform {
  transform(status: string): string {
    return StatusEnum[status as keyof typeof StatusEnum] ?? status;
  }
}
