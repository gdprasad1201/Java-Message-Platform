package com.websocket.java_message_platform.client;

import java.util.ArrayList;

import com.websocket.java_message_platform.Message;

public interface MessageListener {
    void onMessage(Message message);
    void onActivity(ArrayList<String> activeUsers);
}
