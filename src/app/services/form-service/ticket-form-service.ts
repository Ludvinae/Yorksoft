import { inject, Injectable } from '@angular/core';
import { TicketService } from '../api/ticket-service';
import { Ticket } from '../../models/Ticket';
import { ThemeEnum } from '../../models/enum/ThemeEnum';

@Injectable({
  providedIn: 'root',
})
export class TicketFormService {
  private ticketService = inject(TicketService);

  load(id: number) {
    return this.ticketService.get(id);
  }

  mapToForm(ticket: Ticket) {
    return {
      title: ticket.title ?? '',
      description: ticket.description ?? '',
      idUrgency: ticket.idUrgency ?? 0,
      idImpact: ticket.idImpact ?? 0,
      idVersion: ticket.idVersion ?? 0,
    };
  }

  mapToDto(formValue: TicketForm, existing?: Ticket): Ticket {
    return {
      ...formValue,
      idTicket: existing?.idTicket ?? 0,
      title: existing?.title ?? '',
      description: existing?.description ?? '',
      openDate: null,
      modificationDate: null,
      closeDate: null,
      callDuration: null,
      initialPriority: 0,
      finalPriority: 0,
      idClient: existing?.idClient ?? 0,
      idVersion: existing?.idVersion ?? 0,
      idImpact: existing?.idImpact ?? 0,
      idUrgency: existing?.idUrgency ?? 0,
      currentTheme: existing?.currentTheme ?? ThemeEnum.OTHER_ISSUE,
      commentIds: existing?.commentIds ?? [],
      assignmentIds: existing?.assignmentIds ?? [],
      themeIds: existing?.themeIds ?? [],
      historyIds: existing?.historyIds ?? []
    };
  }

  save(dto: Ticket, isEdit: boolean) {
    return isEdit ? this.ticketService.update(dto, dto.idTicket) : this.ticketService.create(dto);
  }

  private formatDate(date: string | Date): string {
    return new Date(date).toISOString().split('T')[0];
  }
}
