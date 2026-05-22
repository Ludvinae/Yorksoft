
type TicketForm = {
  title: FormControl<string>;
  description: FormControl<string>;
  idUrgency: FormControl<number>;
  idImpact: FormControl<number>;
  idVersion: FormControl<number | null>;
  idSoftware: FormControl<number | null>;
  idTheme: FormControl<number>;
};
