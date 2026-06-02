import {Component, Input} from '@angular/core';
import { ActivityType } from '../../models/enum/ActivityType';
import { TimesAgoPipe } from '../../pipe/times-ago-pipe';

@Component({
  selector: 'app-activity-feed',
  imports: [TimesAgoPipe],
  templateUrl: './activity-feed.html',
  styleUrl: './activity-feed.css',
})
export class ActivityFeed {
  @Input({ required: true })
  activities: UserActivity[] = [];
  protected readonly ActivityType = ActivityType;
}
