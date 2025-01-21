package com.websocket.java_message_platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final WebsocketSessionManager sessionManager;

    @Autowired
    public WebsocketController(SimpMessagingTemplate messagingTemplate, WebsocketSessionManager sessionManager) {
        this.messagingTemplate = messagingTemplate;
        this.sessionManager = sessionManager;
    }

    @MessageMapping("/message")
    public void handleMessage(Message message) {
        System.out.println("Recieved message from user: " + message.getUser() +": " + message.getMessage());
        messagingTemplate.convertAndSend("/topic/messages", message);
        System.out.println("Sent message to /topic/messages: " + message.getUser() +": " + message.getMessage());
    }

    @MessageMapping("/connect")
    public void connectUser(String username) {
        sessionManager.addUsername(username);
        sessionManager.broadcastActiveUsernames();
        System.out.println(username + " connected");
    }

    @MessageMapping("/disconnect")
    public void disconnectUser(String username) {
        sessionManager.removeUsername(username);
        sessionManager.broadcastActiveUsernames();
        System.out.println(username + " disconnected");
    }
}
