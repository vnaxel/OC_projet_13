import { Injectable } from '@angular/core';
import { Client, Message } from '@stomp/stompjs';
import { BehaviorSubject } from 'rxjs';
import SockJS from 'sockjs-client';

@Injectable({
    providedIn: 'root'
})
export class WebsocketService {

    constructor() { }

    stompClient: Client | null = null;

    private messageSubject = new BehaviorSubject<any>(null);
    private messages$ = this.messageSubject.asObservable();

    private connectionSubject = new BehaviorSubject<boolean>(false);
    private connection$ = this.connectionSubject.asObservable();

    connect(username: string) {

        const socket = new SockJS('http://localhost:8080/chat-application');

        this.stompClient = new Client({
            webSocketFactory: () => socket,
            reconnectDelay: 5000,
            debug: (str) => console.log(str),
        });

        this.stompClient.onConnect = (frame) => {
            console.log('Connected: ' + frame);
            this.connectionSubject.next(true);
        };

        this.stompClient.onStompError = (frame) => {
            console.error('Broker reported error: ' + frame.headers['message']);
            console.error('Additional details: ' + frame.body);
        };

        this.stompClient.subscribe('/topic/public', (message: Message) => {
            this.messageSubject.next(JSON.parse(message.body));
        });

        this.stompClient.publish({
            destination: '/app/chat.join',
            body: JSON.stringify({ sender: username, type: 'JOIN' }),
        });

        this.stompClient?.activate();
    }

    send(username: string, message: string) {
        if (this.stompClient && this.stompClient.connected) {
            return this.stompClient?.publish({
                destination: '/app/chat.send',
                body: JSON.stringify({ sender: username, content: message, type: 'CHAT' }),
            });
        }
        return console.error('Not connected to websocket');
    }

    disconnect() {
        if (this.stompClient) {
            this.stompClient.deactivate();
        }
    }
}
