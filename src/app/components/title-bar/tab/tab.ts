import { Component } from '@angular/core';

@Component({
  selector: 'app-tab',
  imports: [],
  templateUrl: './tab.html',
  styleUrl: './tab.css',
})
export class Tab {
  tabs = [
    { label: 'TOUS LES TICKETS', value: 'all' },
    { label: 'ATTRIBUÉS', value: 'assigned' },
    { label: 'PRIORITAIRES', value: 'priority' },
  ];

  currentIndex = 0;

  get currentTab() {
    return this.tabs[this.currentIndex];
  }

  nextTab() {
    if (this.currentIndex < this.tabs.length - 1) {
      this.currentIndex++;
    } else {
      this.currentIndex = 0;
    }
  }

  prevTab() {
    if (this.currentIndex > 0) {
      this.currentIndex--;
    } else {
      this.currentIndex = this.tabs.length - 1;
    }
  }
}
