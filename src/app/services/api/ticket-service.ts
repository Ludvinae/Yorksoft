import { inject, Injectable, signal } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Page } from '../../models/page/Page';
import { TicketSearchCriteria } from '../../models/page/TicketSearchCriteria';
import { environment } from '../../../environments/environment';
import { StatusEnum } from '../../models/enum/StatusEnum';

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
  private apiUrl = `${environment.serverUrl}/ticket`;

  readonly ticketList = signal<Ticket[]>([]);
  readonly ticketDetail = signal<TicketListDto[]>([]);

  get(id: number) {
    return this.http.get<Ticket>(`${this.apiUrl}/${id}`);
  }

  getDetail(id: number) {
    return this.http.get<TicketDetailFullDto>(`${this.apiUrl}/${id}/detail`);
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

  getTickets(filters: TicketSearchCriteria, page: number, size: number, sort: string) {
    let params = new HttpParams().set('page', page).set('size', size).set('sort', sort);

    Object.entries(filters).forEach(([key, value]) => {
      if (value !== null && value !== undefined && value !== '') {
        // tableau
        if (Array.isArray(value)) {
          value.forEach((v) => {
            params = params.append(key, v);
          });
        } else {
          params = params.set(key, value.toString());
        }
      }
    });

    return this.http.get<Page<TicketListDto>>(`${environment.serverUrl}/ticket/list`, { params });
  }

  create(ticket: TicketCreation): Observable<Ticket> {
    return this.http.post<Ticket>(`${this.apiUrl}`, ticket);
  }

  // deprecated
  save(ticket: Ticket) {
    return this.http.post<Ticket>(`${this.apiUrl}`, ticket);
  }

  update(ticket: Ticket, id: number) {
    return this.http.put(`${this.apiUrl}/${id}`, ticket).pipe(
      tap((result) => {
        this.getAll().subscribe();
      }),
    );
  }

  patch(id: number, description: string, solution: string) {
    const payload: TicketDescriptionDto = { description: description, solution: solution };

    return this.http.patch<TicketDetailFullDto>(`${this.apiUrl}/${id}/description`, payload)
  }

  delete(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`).pipe(
      tap((result) => {
        this.getAll().subscribe();
      }),
    );
  }

  assign(idTicket: number, idTechnician: number, idManager: number, statusReason: string) {
    const assignment: TicketAssignment = { idTechnician, idManager, statusReason };

    return this.http.post(`${this.apiUrl}/${idTicket}/assign`, assignment).pipe(
      tap((result) => {
        this.getAll().subscribe();
      }),
    );
  }

  solve(idTicket: number, justification: TicketTransition) {
    const payload = { idTechnician: justification.idAppUser, statusReason: justification.reason };
    return this.http.post(`${this.apiUrl}/${idTicket}/solve`, payload);
  }

  progressStart(idTicket: number, justification: TicketTransition) {
    const payload = { idAppUser: justification.idAppUser, statusReason: justification.reason };
    return this.http.post(`${this.apiUrl}/${idTicket}/start-progress`, payload);
  }

  progressResume(idTicket: number, justification: TicketTransition) {
    const payload = { idAppUser: justification.idAppUser, statusReason: justification.reason };
    return this.http.post(`${this.apiUrl}/${idTicket}/resume-progress`, payload);
  }

  waitClient(idTicket: number, justification: TicketTransition) {
    const payload = {idTechnician: justification.idAppUser,
      waitingStatus: StatusEnum.WAITING_CLIENT,
      statusReason: justification.reason}
    return this.http.post(`${this.apiUrl}/${idTicket}/wait`, payload);
  }

  waitThirdParty(idTicket: number, justification: TicketTransition) {
    const payload = {
      idTechnician: justification.idAppUser,
      waitingStatus: StatusEnum.WAITING_THIRD_PARTY,
      statusReason: justification.reason,
    };
    return this.http.post(`${this.apiUrl}/${idTicket}/wait`, payload);
  }

  close(idTicket: number, justification: TicketTransition) {
    const payload = { idAppUser: justification.idAppUser, closingReason: justification.reason };
    return this.http.post(`${this.apiUrl}/${idTicket}/close`, payload);
  }
}
