import { inject, Injectable, signal } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Page } from '../../models/page/Page';
import { TicketSearchCriteria } from '../../models/page/TicketSearchCriteria';

@Injectable({
  providedIn: 'root',
})
/**
 * Appelle l'API pour récuperer les informations sur les tickets,
 * pour les transmettre sous forme d'observable
 *
 * !!! A adapter au nouveau template !!!
 */
export class TicketService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/ticket';

  readonly ticketList = signal<Ticket[]>([]);
  readonly ticketDetail = signal<TicketListDto[]>([]);

  get(id: number) {
    return this.http.get<Ticket>(`${this.apiUrl}/${id}`);
  }

  getDetail(id: number) {
    return this.http.get<TicketListDto>(`${this.apiUrl}/${id}/detail`);
  }

  getAll() {
    return this.http.get<Ticket[]>(`${this.apiUrl}/list`).pipe(
      tap((ticketList) => {
        this.ticketList.set(ticketList);
      }),
    );
  }

  getAllDto() {
    return this.http.get<Page<TicketListDto>>(`${this.apiUrl}/test`).pipe(
      tap((page) => {
        this.ticketDetail.set(page.content);
      }),
    );
  }

  getTickets(
    filters: any,
    page: number,
    size: number,
    sort: string
  ): Observable<Page<TicketListDto>> {

    let params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    if (filters.search?.trim()) {

      params = params.set(
        'search',
        filters.search
      );
    }

    if (filters.assigned) {

      params = params.set(
        'assigned',
        true
      );
    }

    if (filters.priorityOnly) {

      params = params.set(
        'priorityOnly',
        true
      );
    }

    return this.http.get<Page<TicketListDto>>(
      `${this.apiUrl}/test`,
      { params }
    );
  }

  create(ticket: Ticket) {
    return this.http.post(`${this.apiUrl}`, ticket).pipe(tap(() => this.getAll().subscribe()));
  }

  update(ticket: Ticket, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, ticket).pipe(
      tap((result) => {
        this.getAll().subscribe();
      }),
    );
  }

  delete(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`).pipe(
      tap((result) => {
        this.getAll().subscribe();
      }),
    );
  }

}
