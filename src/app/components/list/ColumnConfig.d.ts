type ColumnConfig<T> = {
  label: string;
  field: keyof T;
}
