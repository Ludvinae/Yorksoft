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
  readonly canComment = input(false);
  protected readonly isSubmitting = signal(false);
  protected readonly errorMessage = signal<string | null>(null);

  protected readonly editingCommentId = signal<number | null>(null);
  readonly commentUpdated = output<TicketDetailComment>();
  readonly commentCreated = output<TicketDetailComment>();

  protected createComment(content: string): void {
    if (this.isSubmitting()) {
      return;
    }

    if (!this.authService.jwtInfo()?.id) {
      return;
    }

    this.isSubmitting.set(true);
    this.errorMessage.set(null);

    this.commentService
      .createComment({
        content,
        idTicket: this.ticketId(),
      })
      .pipe(finalize(() => this.isSubmitting.set(false)))
      .subscribe({
        next: (createdComment) => {
          this.commentCreated.emit(createdComment);
        },

        error: (error) => {
          console.error(error);

          if (error.status === 429) {
            // Trial accounts are limited to a number of comments per hour
            const retryAfter = Number(error.headers?.get('Retry-After'));
            const wait = retryAfter ? ` Réessayez dans ${Math.ceil(retryAfter / 60)} min.` : '';
            this.errorMessage.set(`Limite de commentaires atteinte pour un compte d'essai.${wait}`);
          } else {
            this.errorMessage.set("Le commentaire n'a pas pu être envoyé.");
          }
        },
      });
  }

  protected startEdit(idComment: number): void {
    this.editingCommentId.set(idComment);
  }

  protected cancelEdit(): void {
    this.editingCommentId.set(null);
  }

  protected updateComment(idComment: number, content: string): void {
    this.commentService.update({ content: content }, idComment).subscribe({
      next: (updatedComment) => {
        this.commentUpdated.emit(updatedComment);

        this.editingCommentId.set(null);
      },

      error: console.error,
    });
  }
}
