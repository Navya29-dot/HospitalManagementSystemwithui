package com.hms.ui;

import com.hms.dao.StaffDAO;
import com.hms.model.Staff;
import com.hms.db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StaffManagementFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private StaffDAO staffDAO = new StaffDAO();

    public StaffManagementFrame() {
        setTitle("Staff Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table
        model = new DefaultTableModel(
                new Object[]{"Staff ID", "Full Name", "Role", "Phone", "Email"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel panel = new JPanel();
        JButton btnAdd = new JButton("Add Staff");
        JButton btnDelete = new JButton("Delete Staff");
        JButton btnRefresh = new JButton("Refresh");
        panel.add(btnAdd);
        panel.add(btnDelete);
        panel.add(btnRefresh);
        add(panel, BorderLayout.SOUTH);

        loadStaff();

        btnAdd.addActionListener(e -> addStaffForm());
        btnDelete.addActionListener(e -> deleteSelectedStaff());
        btnRefresh.addActionListener(e -> loadStaff());
    }

    private void loadStaff() {
        model.setRowCount(0);
        List<Staff> list = staffDAO.getAllStaff();
        for (Staff s : list) {
            model.addRow(new Object[]{
                    s.getStaffId(),
                    s.getFullName(),
                    s.getRoleType(),
                    s.getPhone(),
                    s.getEmail()
            });
        }
    }

    private void addStaffForm() {
        JTextField fullName = new JTextField();
        JTextField roleType = new JTextField();
        JTextField phone = new JTextField();
        JTextField email = new JTextField();

        Object[] fields = {
                "Full Name:", fullName,
                "Role (Receptionist / Billing / Nurse etc):", roleType,
                "Phone:", phone,
                "Email:", email
        };

        int option = JOptionPane.showConfirmDialog(this, fields,
                "Add New Staff", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Staff s = new Staff();
            s.setFullName(fullName.getText());
            s.setRoleType(roleType.getText());
            s.setPhone(phone.getText());
            s.setEmail(email.getText());

            boolean ok = staffDAO.addStaff(s);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Staff added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add staff!");
            }

            loadStaff();
        }
    }

    private void deleteSelectedStaff() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a staff to delete");
            return;
        }

        int staffId = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete selected staff?", "Are you sure?",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                var con = DBConnection.getConnection();
                var ps = con.prepareStatement("DELETE FROM staff WHERE staff_id = ?");
                ps.setInt(1, staffId);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Staff deleted!");
                    loadStaff();
                } else {
                    JOptionPane.showMessageDialog(this, "No staff found with that ID");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting staff");
            }
        }
    }
}
