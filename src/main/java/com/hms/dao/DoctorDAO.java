package com.hms.dao;

import javax.swing.JOptionPane;
import com.hms.db.DBConnection;
import com.hms.model.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    // Add new doctor
    public boolean addDoctor(Doctor doctor) {
        boolean success = false;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO doctors (full_name, specialization, experience_years, phone, email, department, timings) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, doctor.getFullName());
            ps.setString(2, doctor.getSpecialization());
            ps.setInt(3, doctor.getExperienceYears());
            ps.setString(4, doctor.getPhone());
            ps.setString(5, doctor.getEmail());
            ps.setString(6, doctor.getDepartment());
            ps.setString(7, doctor.getTimings());

            success = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error while adding doctor: " + e.getMessage());
        }
        return success;
    }

    // Get list of doctors
    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT doctor_id, full_name, specialization, experience_years, phone, email, department, timings FROM doctors";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Doctor d = new Doctor();
                d.setDoctorId(rs.getInt("doctor_id"));
                d.setFullName(rs.getString("full_name"));
                d.setSpecialization(rs.getString("specialization"));
                d.setExperienceYears(rs.getInt("experience_years"));
                d.setPhone(rs.getString("phone"));
                d.setEmail(rs.getString("email"));
                d.setDepartment(rs.getString("department"));
                d.setTimings(rs.getString("timings"));

                list.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
