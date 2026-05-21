import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class RouteBuilderService {

  getUpdateRoute(id: number, name: string) {
    return `/admin/${name}/update/${id}`;
  }
}
