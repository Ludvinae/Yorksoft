import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'timesAgo',
})
export class TimesAgoPipe implements PipeTransform {
  transform(value: string | Date): string {
    const now = Date.now();
    const date = new Date(value).getTime();

    const diff = Math.floor((now - date) / 1000);

    if (diff < 60) {
      return "À l'instant";
    }

    if (diff < 3600) {
      return `Il y a ${Math.floor(diff / 60)} min`;
    }

    if (diff < 86400) {
      const hours = Math.floor(diff / 3600);
      const minutes = Math.floor((diff % 3600) / 60);

      return `Il y a ${hours}h${minutes}`;
    }

    if (diff < 172800) {
      return 'Hier';
    }

    return `Il y a ${Math.floor(diff / 86400)} jours`;
  }
}
