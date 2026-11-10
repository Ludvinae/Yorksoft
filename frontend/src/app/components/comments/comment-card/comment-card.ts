import { Component, inject, input, Input, output } from '@angular/core';
import { DatePipe } from '@angular/common';
import { UserRoleLabel } from '../../../pipe/UserRoleLabel';
import { CommentForm } from '../comment-form/comment-form';
import { AuthService } from '../../../services/business/auth-service';
import { Avatar } from '../../avatar/avatar';

@Component({
  selector: 'app-comment-card',
  imports: [DatePipe, UserRoleLabel, CommentForm, Avatar],
  templateUrl: './comment-card.html',
  styleUrl: './comment-card.css',
})
export class CommentCard {
  auth = inject(AuthService);

  @Input() comment: TicketDetailComment | undefined;

  readonly isEditing = input(false);

  readonly edit = output<void>();
  readonly save = output<string>();
  readonly cancel = output<void>();

  protected readonly currentUserName = this.auth.jwtInfo()?.name;

  protected wasEdited(): boolean {
    if (!this.comment) {
      return false;
    }

    return (
      new Date(this.comment.dateSent).getTime() !==
      new Date(this.comment.lastModification).getTime()
    );
  }
}
