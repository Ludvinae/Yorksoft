// Mirrors backend dto/dashboard/graphs/SlaPointDto
type SlaPoint = {
  date: string; // YYYY-MM-DD, first day of the week or month
  onTime: number;
  late: number;
};
