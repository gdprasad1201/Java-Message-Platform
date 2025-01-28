package com.websocket.java_message_platform.client;

import java.awt.Color;

import javax.swing.border.EmptyBorder;

public class Utilities {
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    public static final Color PRIMARY = Color.decode("#2F2D2D");
    public static final Color SECONDARY = Color.decode("#484444");
    public static final Color TEXT_COLOR = Color.WHITE;

    public static EmptyBorder addPadding(int top, int left, int bottom, int right) {
        return new EmptyBorder(top, left, bottom, right);
    }
}

