package com.websocket.java_message_platform.client;

import java.util.concurrent.ExecutionException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import com.websocket.java_message_platform.Message;

public class ClientGUI extends JFrame {
    // public static void main(String[] args) throws InterruptedException, ExecutionException {
    //     MyStompClient myStompClient = new MyStompClient("TapTap");
    //     myStompClient.sendMessage(new Message("TapTap", "Hello World!"));
    //     myStompClient.disconnectUser("TapTap");
    // }

    private JPanel connectedUsersPanel;

    public ClientGUI(String username) {
        super("Java Message Platform - " + username);

        setSize(1218, 685);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(ClientGUI.this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    ClientGUI.this.dispose();
                }
                else {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }
        });

        getContentPane().setBackground(Utilities.PRIMARY);
        addGUIComponents();
    }

    private void addGUIComponents() {
        addConnectedUserComponents();
    }

    private void addConnectedUserComponents() {
        connectedUsersPanel = new JPanel();
        connectedUsersPanel.setLayout(new BoxLayout(connectedUsersPanel, BoxLayout.Y_AXIS));
        connectedUsersPanel.setBackground(Utilities.SECONDARY);
        connectedUsersPanel.setPreferredSize(new Dimension(200, getHeight()));

        JLabel connectedUsersLabel = new JLabel("Connected Users");
        connectedUsersLabel.setFont(new Font("Inter", Font.BOLD, 18));
        connectedUsersLabel.setForeground(Utilities.TEXT_COLOR);
        connectedUsersPanel.add(connectedUsersLabel);

        add(connectedUsersPanel, BorderLayout.WEST);
    }
}
