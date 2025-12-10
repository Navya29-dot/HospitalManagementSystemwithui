package com.hms.dao;

import com.hms.db.DBConnection;
import com.hms.model.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    // Add new staff
    public boolean addStaff(Staff s) {
        boolean success = false;

        // user_id + baaki columns
        String sql = "INSERT INTO staff (user_id, full_name, role_type, phone, email) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            // 1) user_id (INT) -> abhi ke liye fix value 1 ya koi valid user_id
            ps.setInt(1, 1);

            // 2) full_name
            ps.setString(2, s.getFullName());

            // 3) role_type
            ps.setString(3, s.getRoleType());

            // 4) phone
            ps.setString(4, s.getPhone());

            // 5) email
            ps.setString(5, s.getEmail());

            success = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }


    // Get staff list
    public List<Staff> getAllStaff() {
        List<Staff> list = new ArrayList<>();

        String sql = "SELECT staff_id, full_name, role_type, phone, email FROM staff";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Staff s = new Staff();
                s.setStaffId(rs.getInt("staff_id"));
                s.setFullName(rs.getString("full_name"));
                s.setRoleType(rs.getString("role_type"));
                s.setPhone(rs.getString("phone"));
                s.setEmail(rs.getString("email"));
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Delete staff
    public boolean deleteStaff(int staffId) {
        boolean success = false;

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM staff WHERE staff_id = ?");
            ps.setInt(1, staffId);

            success = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
}
