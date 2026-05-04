import { Component } from '@angular/core';
import {FieldSelect} from "../../../../components/form/field-select/field-select";
import {FieldText} from "../../../../components/form/field-text/field-text";
import { GenericFormPage } from '../../../../components/form/generic-form-page/generic-form-page';

@Component({
  selector: 'app-version-type-edit',
  imports: [FieldSelect, FieldText, GenericFormPage],
  templateUrl: './version-type-edit.html',
  styleUrl: './version-type-edit.css',
})
export class VersionTypeEdit {}
