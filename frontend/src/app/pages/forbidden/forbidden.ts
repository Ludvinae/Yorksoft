import { Component } from '@angular/core';
import { CenteredPage } from '../../components/layout/centered-page/centered-page';
import { BackButton } from '../../components/layout/back-button/back-button';

@Component({
  selector: 'app-forbidden',
  imports: [CenteredPage, BackButton],
  templateUrl: './forbidden.html',
  styleUrl: './forbidden.css',
})
export class Forbidden {}
