package com.hms.ui;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard - Hospital Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lbl = new JLabel("Welcome Admin");
        lbl.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lbl, gbc);

        gbc.gridwidth = 1;

        JButton btnDoctors = new JButton("Manage Doctors");
        gbc.gridx = 0; gbc.gridy = 1;
        add(btnDoctors, gbc);

        JButton btnStaff = new JButton("Manage Staff");
        gbc.gridx = 1; gbc.gridy = 1;
        add(btnStaff, gbc);

        JButton btnPatients = new JButton("Manage Patients");
        gbc.gridx = 0; gbc.gridy = 2;
        add(btnPatients, gbc);

        JButton btnAppointments = new JButton("Manage Appointments");
        gbc.gridx = 1; gbc.gridy = 2;
        add(btnAppointments, gbc);

        JButton btnBilling = new JButton("Manage Billing");
        gbc.gridx = 0; gbc.gridy = 3;
        add(btnBilling, gbc);

        JButton btnLogout = new JButton("Logout");
        gbc.gridx = 1; gbc.gridy = 3;
        add(btnLogout, gbc);

        // CLICK ACTIONS
        btnDoctors.addActionListener(e -> {
            new DoctorManagementFrame().setVisible(true);
        });


        btnStaff.addActionListener(e -> {
            new StaffManagementFrame().setVisible(true);
        });



        btnPatients.addActionListener(e -> {
            new PatientManagementFrame().setVisible(true);
        });


        btnAppointments.addActionListener(e -> {
            new AppointmentManagementFrame().setVisible(true);
        });


        btnBilling.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Opening Billing Management...");
            // new BillingFrame().setVisible(true);
        });

        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }
}
