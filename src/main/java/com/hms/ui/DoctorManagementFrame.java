package com.hms.ui;

import com.hms.dao.DoctorDAO;
import com.hms.model.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DoctorManagementFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private DoctorDAO doctorDAO = new DoctorDAO();

    public DoctorManagementFrame() {
        setTitle("Doctor Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table
        model = new DefaultTableModel(
                new Object[]{"Doctor ID", "Full Name", "Specialization", "Experience", "Phone"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel panel = new JPanel();
        JButton btnAdd = new JButton("Add Doctor");
        JButton btnDelete = new JButton("Delete Doctor");
        JButton btnRefresh = new JButton("Refresh");
        panel.add(btnAdd);
        panel.add(btnDelete);
        panel.add(btnRefresh);
        add(panel, BorderLayout.SOUTH);

        loadDoctors(); // show list initially

        // Add doctor form
        btnAdd.addActionListener(e -> addDoctorForm());

        // Delete doctor
        btnDelete.addActionListener(e -> deleteSelectedDoctor());

        // Refresh list
        btnRefresh.addActionListener(e -> loadDoctors());
    }

    private void loadDoctors() {
        model.setRowCount(0);
        List<Doctor> list = doctorDAO.getAllDoctors();
        for (Doctor d : list) {
            model.addRow(new Object[]{
                    d.getDoctorId(), d.getFullName(), d.getSpecialization(),
                    d.getExperienceYears(), d.getPhone()
            });
        }
    }

    private void addDoctorForm() {
        JTextField fullName = new JTextField();
        JTextField specialization = new JTextField();
        JTextField experience = new JTextField();
        JTextField phone = new JTextField();
        JTextField email = new JTextField();
        JTextField dept = new JTextField();
        JTextField timings = new JTextField();

        Object[] fields = {
                "Full Name:", fullName,
                "Specialization:", specialization,
                "Experience (years):", experience,
                "Phone:", phone,
                "Email:", email,
                "Department:", dept,
                "Timings:", timings
        };

        int option = JOptionPane.showConfirmDialog(this, fields,
                "Add New Doctor", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Doctor d = new Doctor();
            d.setFullName(fullName.getText());
            d.setSpecialization(specialization.getText());
            d.setExperienceYears(Integer.parseInt(experience.getText()));
            d.setPhone(phone.getText());
            d.setEmail(email.getText());
            d.setDepartment(dept.getText());
            d.setTimings(timings.getText());

            boolean ok = doctorDAO.addDoctor(d);
            if (ok) JOptionPane.showMessageDialog(this, "Doctor added successfully!");
            else JOptionPane.showMessageDialog(this, "Failed to add doctor!");

            loadDoctors();
        }
    }

    private void deleteSelectedDoctor() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a doctor to delete");
            return;
        }

        int doctorId = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete selected doctor?", "Are you sure?",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                var con = com.hms.db.DBConnection.getConnection();
                var ps = con.prepareStatement("DELETE FROM doctors WHERE doctor_id = ?");
                ps.setInt(1, doctorId);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Doctor deleted!");
                    loadDoctors();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting doctor");
            }
        }
    }
}

