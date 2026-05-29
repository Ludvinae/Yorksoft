import { Component, Input } from '@angular/core';
import { DatePipe } from '@angular/common';
import { UserRoleLabel } from '../../../pipe/UserRoleLabel';

@Component({
  selector: 'app-comment-card',
  imports: [DatePipe, UserRoleLabel],
  templateUrl: './comment-card.html',
  styleUrl: './comment-card.css',
})
export class CommentCard {
  @Input() comment: TicketDetailComment | undefined;
}
