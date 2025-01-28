package com.websocket.java_message_platform.client;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import com.websocket.java_message_platform.Message;

public class ClientGUI extends JFrame implements MessageListener{
    // public static void main(String[] args) throws InterruptedException, ExecutionException {
    //     MyStompClient myStompClient = new MyStompClient("TapTap");
    //     myStompClient.sendMessage(new Message("TapTap", "Hello World!"));
    //     myStompClient.disconnectUser("TapTap");
    // }

    private JPanel connectedUsersPanel, messgaePanel;
    private MyStompClient myStompClient;
    private String username;

    public ClientGUI(String username) throws InterruptedException, ExecutionException {
        super("User: " + username);
        this.username = username;
        this.myStompClient = new MyStompClient(this, username);

        setSize(1218, 685);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(ClientGUI.this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    myStompClient.disconnectUser(username);
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
        addChatComponents();
    }

    private void addConnectedUserComponents() {
        connectedUsersPanel = new JPanel();
        connectedUsersPanel.setBorder(Utilities.addPadding(10, 10, 10, 10));
        connectedUsersPanel.setLayout(new BoxLayout(connectedUsersPanel, BoxLayout.Y_AXIS));
        connectedUsersPanel.setBackground(Utilities.SECONDARY);
        connectedUsersPanel.setPreferredSize(new Dimension(200, getHeight()));

        JLabel connectedUsersLabel = new JLabel("Connected Users");
        connectedUsersLabel.setFont(new Font("Inter", Font.BOLD, 18));
        connectedUsersLabel.setForeground(Utilities.TEXT_COLOR);
        connectedUsersPanel.add(connectedUsersLabel);

        add(connectedUsersPanel, BorderLayout.WEST);
    }

    private void addChatComponents() {
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        chatPanel.setBackground(Utilities.TRANSPARENT);

        messgaePanel = new JPanel();
        messgaePanel.setLayout(new BoxLayout(messgaePanel, BoxLayout.Y_AXIS));
        messgaePanel.setBackground(Utilities.TRANSPARENT);
        chatPanel.add(messgaePanel, BorderLayout.CENTER);

        // messgaePanel.add(createMessageComponent(new Message("gdprasad", "Hello World!")));

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(Utilities.addPadding(10, 10, 10, 10));
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBackground(Utilities.TRANSPARENT);

        JTextField inputField = new JTextField();
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    String message = inputField.getText();

                    if (message.isEmpty()) {
                        return;
                    }

                    inputField.setText("");

                    myStompClient.sendMessage(new Message(username, message));
                }
            }
        });
        inputField.setBackground(Utilities.SECONDARY);
        inputField.setForeground(Utilities.TEXT_COLOR);
        inputField.setBorder(Utilities.addPadding(0, 10, 0, 10));
        inputField.setFont(new Font("Inter", Font.PLAIN, 16));
        inputField.setPreferredSize(new Dimension(inputPanel.getWidth(), 50));
        inputPanel.add(inputField, BorderLayout.CENTER);

        chatPanel.add(inputPanel, BorderLayout.SOUTH);

        add(chatPanel, BorderLayout.CENTER);
    }

    private JPanel createMessageComponent(Message message) {
        JPanel chatMessage = new JPanel();
        chatMessage.setLayout(new BoxLayout(chatMessage, BoxLayout.Y_AXIS));
        chatMessage.setBackground(Utilities.TRANSPARENT);
        chatMessage.setBorder(Utilities.addPadding(20, 20, 10, 20));

        JLabel usernameLabel = new JLabel(message.getUser());
        usernameLabel.setFont(new Font("Inter", Font.BOLD, 18));
        usernameLabel.setForeground(Utilities.TEXT_COLOR);
        chatMessage.add(usernameLabel);

        JLabel messageLabel = new JLabel(message.getMessage());
        messageLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        messageLabel.setForeground(Utilities.TEXT_COLOR);
        chatMessage.add(messageLabel);

        return chatMessage;
    }

    @Override
    public void onMessage(Message message) {
        messgaePanel.add(createMessageComponent(message));
        revalidate();
        repaint();
    }

    @Override
    public void onActivity(ArrayList<String> activeUsers) {
        if (connectedUsersPanel.getComponents().length >= 2) {
            connectedUsersPanel.remove(1);
        }

        JPanel usersPanel = new JPanel();
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));
        usersPanel.setBackground(Utilities.TRANSPARENT);
        usersPanel.setBorder(Utilities.addPadding(10, 10, 10, 10));
    
        for (String string : activeUsers) {
            JLabel username = new JLabel();
            username.setText(string);
            username.setFont(new Font("Inter", Font.BOLD, 16));
            username.setForeground(Utilities.TEXT_COLOR);
            usersPanel.add(username);
        }

        connectedUsersPanel.add(usersPanel);
        revalidate();
        repaint();
    }
}
