import { Pipe, PipeTransform } from '@angular/core';
import { formatDistanceToNow } from 'date-fns';
import { fr } from 'date-fns/locale';

@Pipe({
  name: 'timesAgo',
})
export class TimesAgoPipe implements PipeTransform {
  transform(value: string | Date): string {
    return formatDistanceToNow(new Date(value), {
      addSuffix: true,
      locale: fr,
    });
  }
}
