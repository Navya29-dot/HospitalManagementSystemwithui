package com.hms.ui;

import com.hms.dao.PatientDAO;
import com.hms.model.Patient;
import com.hms.db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientManagementFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private PatientDAO patientDAO = new PatientDAO();

    public PatientManagementFrame() {
        setTitle("Patient Management");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table model
        model = new DefaultTableModel(
                new Object[]{"Patient ID", "Full Name", "Age", "Gender", "Phone", "Condition", "Status"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons panel
        JPanel panel = new JPanel();
        JButton btnAdd = new JButton("Add Patient");
        JButton btnDelete = new JButton("Delete Patient");
        JButton btnRefresh = new JButton("Refresh");
        panel.add(btnAdd);
        panel.add(btnDelete);
        panel.add(btnRefresh);
        add(panel, BorderLayout.SOUTH);

        // Load initial data
        loadPatients();

        // Button actions
        btnAdd.addActionListener(e -> addPatientForm());
        btnDelete.addActionListener(e -> deleteSelectedPatient());
        btnRefresh.addActionListener(e -> loadPatients());
    }

    private void loadPatients() {
        model.setRowCount(0);
        List<Patient> list = patientDAO.getAllPatients();
        for (Patient p : list) {
            model.addRow(new Object[]{
                    p.getPatientId(),
                    p.getFullName(),
                    p.getAge(),
                    p.getGender(),
                    p.getPhone(),
                    p.getConditionDesc(),
                    p.getStatus()
            });
        }
    }

    private void addPatientForm() {
        JTextField fullName = new JTextField();
        JTextField age = new JTextField();
        JTextField gender = new JTextField();
        JTextField phone = new JTextField();
        JTextField address = new JTextField();
        JTextField condition = new JTextField();
        JTextField admittedOn = new JTextField();
        JTextField status = new JTextField();

        Object[] fields = {
                "Full Name:", fullName,
                "Age:", age,
                "Gender:", gender,
                "Phone:", phone,
                "Address:", address,
                "Condition / Problem:", condition,
                "Admitted on (YYYY-MM-DD, blank if none):", admittedOn,
                "Status (ACTIVE / DISCHARGED):", status
        };

        int option = JOptionPane.showConfirmDialog(this, fields,
                "Add New Patient", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                Patient p = new Patient();
                p.setFullName(fullName.getText());
                p.setAge(Integer.parseInt(age.getText()));
                p.setGender(gender.getText());
                p.setPhone(phone.getText());
                p.setAddress(address.getText());
                p.setConditionDesc(condition.getText());

                String date = admittedOn.getText().trim();
                if (date.isEmpty()) {
                    p.setAdmittedOn(null);
                } else {
                    p.setAdmittedOn(date);
                }

                String st = status.getText().trim();
                if (st.isEmpty()) st = "ACTIVE";
                p.setStatus(st);

                boolean ok = patientDAO.addPatient(p);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Patient added successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add patient!");
                }

                loadPatients();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Age must be a number");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void deleteSelectedPatient() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a patient to delete");
            return;
        }

        int patientId = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete selected patient?", "Are you sure?",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                var con = DBConnection.getConnection();
                var ps = con.prepareStatement("DELETE FROM patients WHERE patient_id = ?");
                ps.setInt(1, patientId);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Patient deleted!");
                    loadPatients();
                } else {
                    JOptionPane.showMessageDialog(this, "No patient found with that ID");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting patient");
            }
        }
    }
}

