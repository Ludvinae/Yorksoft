export interface TicketSearchCriteria {
  statuses?: string[];

  statusesExcluded?: string[];

  hasClient?: number;

  hasVersion?: number;

  hasSoftware?: number;

  keyword?: string;

  openedAfter?: string;

  openedBefore?: string;

  closedAfter?: string;

  closedBefore?: string;

  priorityGreaterThan?: number;

  priorityLessThan?: number;

  priorityEquals?: number;

  assignedTo?: number;

  isNotClosed?: boolean;

  isOverdue?: boolean;

  hasStatusMismatch?: boolean;
}
