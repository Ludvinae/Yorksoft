type TicketFullLatest = {
  id: number;
  openDate: Date;
  closeDate: Date;
  modificationDate: Date;
  description: string;
  callDuration: number;
  initialPriority: number;
  finalPriority: number;
  urgencyDesignation: string;
  urgencyPriority: number;
  impactDesignation: string;
  impactPriority: number;
  versionNumber: string;
  versionTypeDesignation: string;
  softwareName: string;
  clientId: number;
  clientFirstName: string;
  clientLastName: string;
  clientImportance: number;
  themeDesignation: string;
  themePriority: number;
  statusDesignation: string;
  comment: string;
  commentAuthorFirstName: string;
  commentAuthorLastName: string;
}
