package com.hms.dao;

import com.hms.db.DBConnection;
import com.hms.model.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    // Add new staff (admin use karega)
    public boolean addStaff(Staff s) {
        boolean success = false;

        String sql = "INSERT INTO staff (user_id, full_name, role_type, phone, email) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, s.getUserId());
            ps.setString(2, s.getFullName());
            ps.setString(3, s.getRoleType());
            ps.setString(4, s.getPhone());
            ps.setString(5, s.getEmail());

            success = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    // List of all staff (for Admin -> Staff page)
    public List<Staff> getAllStaff() {
        List<Staff> list = new ArrayList<>();

        String sql = "SELECT * FROM staff";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Staff s = new Staff();
                s.setStaffId(rs.getInt("staff_id"));
                s.setUserId(rs.getInt("user_id"));
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
}
