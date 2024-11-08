import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { WebsocketService } from './services/websocket.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'frontend';

  constructor(private websocketService: WebsocketService) {
        console.log('App Component constructor called');
  }

    ngOnInit() {
        console.log('App Component initialized');
    }

    connect() {
        console.log('Connect button clicked');
    }

    send() {
        console.log('Send button clicked');
    }
}
