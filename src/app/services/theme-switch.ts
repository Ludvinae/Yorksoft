import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ThemeSwitch {

  private currentTheme = 'light';

  constructor() {
    this.loadTheme();
  }

  setTheme(theme: string) {
    this.currentTheme = theme;
    document.documentElement.setAttribute('data-theme', theme);
    localStorage.setItem('theme', theme);
  }

  toggleTheme() {
    const newTheme = this.currentTheme === 'light' ? 'dark' : 'light';
    this.setTheme(newTheme);
  }

  loadTheme() {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme) {
      this.setTheme(savedTheme);
    } else {
      this.setTheme('light');
    }
  }

  getTheme() {
    return this.currentTheme;
  }
}
