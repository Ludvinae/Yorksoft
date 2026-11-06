// Mirrors backend dto/dashboard/graphs/TicketEvolutionPointDto
type TicketEvolutionPoint = {
  date: string; // YYYY-MM-DD, first day of the day or week bucket
  takenInCharge: number;
  closed: number;
};
