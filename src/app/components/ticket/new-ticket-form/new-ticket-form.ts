import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-new-ticket-form',
  imports: [],
  templateUrl: './new-ticket-form.html',
  styleUrl: './new-ticket-form.css',
})
export class NewTicketForm {
  @Input({ required: true })
  client!: ClientListDto;
}
