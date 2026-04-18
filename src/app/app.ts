import { Component, inject, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from './components/nav/header/header';
import { Auth } from './services/auth';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Header],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {

  private auth = inject(Auth);

  protected readonly title = signal('Suivi-MNS-Frontend');

  isAuthenticated = this.auth.isAuthenticated;


}
