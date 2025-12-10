package com.hms.ui;

import com.hms.dao.AppointmentDAO;
import com.hms.model.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AppointmentManagementFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    public AppointmentManagementFrame() {
        setTitle("Appointment Management");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table
        model = new DefaultTableModel(
                new Object[]{"ID", "Patient ID", "Doctor ID", "DateTime", "Status", "Description"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom buttons
        JPanel panel = new JPanel();
        JButton btnAdd = new JButton("Book Appointment");
        JButton btnRefresh = new JButton("Refresh");
        panel.add(btnAdd);
        panel.add(btnRefresh);
        add(panel, BorderLayout.SOUTH);

        // Load existing appointments
        loadAppointments();

        // Button actions
        btnAdd.addActionListener(e -> addAppointmentForm());
        btnRefresh.addActionListener(e -> loadAppointments());
    }

    private void loadAppointments() {
        model.setRowCount(0);
        List<Appointment> list = appointmentDAO.getAllAppointments();
        for (Appointment a : list) {
            model.addRow(new Object[]{
                    a.getAppointmentId(),
                    a.getPatientId(),
                    a.getDoctorId(),
                    a.getAppointmentDatetime(),
                    a.getStatus(),
                    a.getDescription()
            });
        }
    }

    private void addAppointmentForm() {
        JTextField patientId = new JTextField();
        JTextField doctorId = new JTextField();
        JTextField datetime = new JTextField();
        JTextField desc = new JTextField();

        Object[] fields = {
                "Patient ID:", patientId,
                "Doctor ID:", doctorId,
                "Appointment DateTime (YYYY-MM-DD HH:MM:SS):", datetime,
                "Reason / Description:", desc
        };

        int option = JOptionPane.showConfirmDialog(this, fields,
                "Book New Appointment", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                Appointment a = new Appointment();
                a.setPatientId(Integer.parseInt(patientId.getText()));
                a.setDoctorId(Integer.parseInt(doctorId.getText()));
                a.setAppointmentDatetime(datetime.getText());
                a.setDescription(desc.getText());
                a.setStatus("SCHEDULED");

                boolean ok = appointmentDAO.addAppointment(a);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Appointment booked successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to book appointment!");
                }

                loadAppointments();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Patient ID and Doctor ID must be numbers");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
}

