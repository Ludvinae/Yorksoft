import { Component, inject, Input } from '@angular/core';
import { KnowledgeService } from '../../services/api/knowledge-service';

@Component({
  selector: 'app-knowledge-selector',
  imports: [],
  templateUrl: './knowledge-selector.html',
  styleUrl: './knowledge-selector.css',
})
export class KnowledgeSelector {
  knowledgeService = inject(KnowledgeService);

  @Input() idTheme: number = 0;
  @Input() idVersion: number = 0;
  @Input() selectedKnowledge: TicketDetailKnowledge | undefined;
}
