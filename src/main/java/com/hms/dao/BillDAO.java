package com.hms.dao;

import com.hms.db.DBConnection;
import com.hms.model.Bill;
import com.hms.model.BillItem;

import java.sql.*;
import java.util.List;

public class BillDAO {


    public int createBill(Bill bill, int staffId) {
        int generatedId = -1;

        String sql = "INSERT INTO bills (patient_id, appointment_id, total_amount, created_at, generated_by_staff_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, bill.getPatientId());
            ps.setInt(2, bill.getAppointmentId());
            ps.setDouble(3, bill.getTotalAmount());
            ps.setString(4, bill.getCreatedAt());
            ps.setInt(5, staffId);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedId;
    }


    public boolean addBillItems(int billId, List<BillItem> items) {
        boolean success = false;

        String sql = "INSERT INTO bill_items (bill_id, item_type, description, quantity, amount) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            for (BillItem item : items) {
                ps.setInt(1, billId);
                ps.setString(2, item.getItemType());
                ps.setString(3, item.getDescription());
                ps.setInt(4, item.getQuantity());
                ps.setDouble(5, item.getAmount());
                ps.addBatch();
            }

            ps.executeBatch();
            success = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }
}
