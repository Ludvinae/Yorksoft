import { Component, inject, OnInit } from '@angular/core';
import {TicketList} from '../../components/tickets/ticket-list/ticket-list';
import { Auth } from '../../services/auth';
import { Tab } from '../../components/title-bar/tab/tab';
import { BreakpointSwitch } from '../../services/breakpoint-switch';
import { TitleBar } from '../../components/title-bar/title-bar/title-bar';

@Component({
  selector: 'app-tickets',
  imports: [TicketList, Tab, TitleBar],
  templateUrl: './tickets.html',
  styleUrl: './tickets.css',
})
export class Tickets {
  private breakpoint = inject(BreakpointSwitch);

  isMobile = this.breakpoint.isMobile;
}
