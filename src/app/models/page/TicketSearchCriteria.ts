export interface TicketSearchCriteria {
  statuses?: string[];

  statusesExcluded?: string[];

  clientId?: number;

  versionId?: number;

  softwareId?: number;

  keyword?: string;

  openedAfter?: string;

  openedBefore?: string;

  closedAfter?: string;

  closedBefore?: string;

  priorityGreaterThan?: number;

  priorityLessThan?: number;

  priorityEquals?: number;

  assignedTo?: number;
}
