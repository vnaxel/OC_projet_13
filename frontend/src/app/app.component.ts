import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { WebsocketService } from './services/websocket.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [CommonModule, FormsModule, HttpClientModule],
    templateUrl: './app.component.html',
    styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

    title = 'yourcaryourway';
    username: string = '';
    message: string = '';
    messages: any[] = [];
    isConnected = false;
    connectingMessage = 'Connecting...';

    constructor(private websocketService: WebsocketService) { }

    ngOnInit() {
        this.websocketService.fetchOldMessages().subscribe(oldMessages => {
            this.messages = oldMessages;
        });

        this.websocketService.messages$.subscribe(message => {
            if (message) {
                this.messages.push(message);
            }
        });

        this.websocketService.connection$.subscribe(connected => {
            this.isConnected = connected;
            if (connected) {
                this.connectingMessage = ''; 
                console.log('WebSocket connection established');
              }
        });
    }

    connect() {
        this.websocketService.connect(this.username);
    }

    send() {
        if (this.message) {
            this.websocketService.send(this.username, this.message);
            this.message = '';
        }
    }
}
