type Ticket = {
  id: number;
  openDate: Date;
  closeDate: Date;
  modificationDate: Date;
  description: string;
  callDuration: number;
  initialPriority: number;
  finalPriority: number;
  urgency: Urgency;
  impact: Impact;
  classificationList: List<Classification>;
}
