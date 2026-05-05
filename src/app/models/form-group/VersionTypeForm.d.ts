import { FormControl } from '@angular/forms';

type VersionTypeForm = {
  designation: FormControl<string>;
  urgencyMalus: FormControl<number | null>;
}
