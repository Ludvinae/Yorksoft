import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

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

  getTicketsFull(): Observable<TicketFullLatest[]> {
    return this.http.get<TicketFullLatest[]>(`${this.apiUrl}/list/full`);
  }

  getTicketsPriority(): Observable<TicketFullLatest[]> {
    return this.http.get<TicketFullLatest[]>(`${this.apiUrl}/list/priority`);
  }

  getTicketsAssigned(): Observable<TicketFullLatest[]> {
    return this.http.get<TicketFullLatest[]>(`${this.apiUrl}/list/assigned`);
  }

  getTickets(tab: string): Observable<TicketFullLatest[]> {
    return this.http.get<TicketFullLatest[]>(`${this.apiUrl}/list-` + tab);
  }
}
