import {Component, Input} from '@angular/core';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-activity-feed',
  imports: [
    DatePipe
  ],
  templateUrl: './activity-feed.html',
  styleUrl: './activity-feed.css',
})
export class ActivityFeed {

  @Input({ required: true })
  activities: UserActivity[] = [];
}
