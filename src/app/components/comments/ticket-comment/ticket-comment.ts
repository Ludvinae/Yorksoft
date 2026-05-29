import {
  Component,
  inject,
  input,
  output,
  signal,
} from '@angular/core';
import { CommentCard } from '../comment-card/comment-card';
import { CommentForm } from '../comment-form/comment-form';
import { CommentService } from '../../../services/api/comment-service';
import { finalize } from 'rxjs';
import { AuthService } from '../../../services/business/auth-service';

@Component({
  selector: 'app-ticket-comment',
  imports: [CommentCard, CommentForm],
  templateUrl: './ticket-comment.html',
  styleUrl: './ticket-comment.css',
})
export class TicketComment {
  commentService = inject(CommentService);
  authService: AuthService = inject(AuthService);

  readonly comments = input<TicketDetailComment[]>([]);
  readonly ticketId = input.required<number>();
  protected readonly isSubmitting = signal(false);

  readonly commentCreated = output<TicketDetailComment>();

  protected createComment(content: string): void {
    if (this.isSubmitting()) {
      return;
    }

    if (!this.authService.jwtInfo()?.id) {
      return;
    }

    this.isSubmitting.set(true);

    this.commentService
      .createComment({
        content,
        idTicket: this.ticketId()
      })
      .pipe(finalize(() => this.isSubmitting.set(false)))
      .subscribe({
        next: (createdComment) => {
          this.commentCreated.emit(createdComment);
        },

        error: (error) => {
          console.error(error);

          // toast plus tard
        },
      });
  }
}
