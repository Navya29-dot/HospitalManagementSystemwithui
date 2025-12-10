package com.hms.ui;

import com.hms.dao.UserDAO;
import com.hms.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;

    public LoginFrame() {
        setTitle("Hospital Management System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitle = new JLabel("Login");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblTitle, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        txtUser = new JTextField(15);
        add(txtUser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        txtPass = new JPasswordField(15);
        add(txtPass, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        btnLogin = new JButton("Login");
        add(btnLogin, gbc);

        // LOGIN BUTTON CLICK
        btnLogin.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username & password");
            return;
        }


        UserDAO dao = new UserDAO();
        User user = dao.login(username, password);
        int role = user.getRoleId();

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
            return;
        }

        JOptionPane.showMessageDialog(this, "Login Successful!");



        if (role == 1) {
            JOptionPane.showMessageDialog(this, "Welcome Admin!");
            dispose();
            new AdminDashboard().setVisible(true);
        } else if (role == 2) {
            JOptionPane.showMessageDialog(this, "Welcome Doctor!");
            dispose();
            // new DoctorDashboard().setVisible(true);
        } else if (role == 3) {
            JOptionPane.showMessageDialog(this, "Welcome Staff!");
            dispose();
            // new StaffDashboard().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new LoginFrame().setVisible(true);
    }
}
