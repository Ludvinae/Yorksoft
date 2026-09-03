
type StatusForm = {
  designation: FormControl<string>,
  code: FormControl<StatusEnum>,
  displayOrder: FormControl<number | null>,
}
