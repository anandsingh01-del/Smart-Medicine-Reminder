package com.project.ui;

import com.project.service.UserService;
import com.project.service.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI {

    public LoginUI() {

        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel title = new JLabel("Login", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Email
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        JTextField emailField = new JTextField(15);
        panel.add(emailField, gbc);

        // Password
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // Buttons
        gbc.gridy = 3;
        gbc.gridx = 0;
        JButton loginButton = new JButton("Login");
        panel.add(loginButton, gbc);

        gbc.gridx = 1;
        JButton registerButton = new JButton("Register");
        panel.add(registerButton, gbc);

        // ================= LOGIN ACTION =================
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                    return;
                }

                int userId = UserService.login(email, password);

                if (userId != -1) {
                    Session.currentUserId = userId;

                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                    frame.dispose();

                    new DashboardUI(); // open dashboard

                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Credentials!");
                }
            }
        });

        // ================= REGISTER ACTION =================
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                    return;
                }

                // Using email as name temporarily
                boolean success = UserService.register(email, email, password);

                if (success) {
                    JOptionPane.showMessageDialog(frame, "Registered Successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "User already exists!");
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}