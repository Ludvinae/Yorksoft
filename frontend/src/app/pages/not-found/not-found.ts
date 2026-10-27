import { Component } from '@angular/core';
import { CenteredPage } from '../../components/layout/centered-page/centered-page';
import { BackButton } from '../../components/layout/back-button/back-button';

@Component({
  selector: 'app-not-found',
  imports: [CenteredPage, BackButton],
  templateUrl: './not-found.html',
  styleUrl: './not-found.css',
})
export class NotFound {}
