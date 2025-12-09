package com.hms.dao;

import com.hms.db.DBConnection;
import com.hms.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // Add new patient (for staff)
    public boolean addPatient(Patient p) {
        boolean success = false;

        String sql = "INSERT INTO patients " +
                "(full_name, age, gender, phone, address, condition_desc, admitted_on, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getFullName());
            ps.setInt(2, p.getAge());
            ps.setString(3, p.getGender());
            ps.setString(4, p.getPhone());
            ps.setString(5, p.getAddress());
            ps.setString(6, p.getConditionDesc());
            ps.setString(7, p.getAdmittedOn());
            ps.setString(8, p.getStatus());

            success = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    // List of all patients (for Patients page)
    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();

        String sql = "SELECT * FROM patients";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Patient p = new Patient();
                p.setPatientId(rs.getInt("patient_id"));
                p.setFullName(rs.getString("full_name"));
                p.setAge(rs.getInt("age"));
                p.setGender(rs.getString("gender"));
                p.setPhone(rs.getString("phone"));
                p.setAddress(rs.getString("address"));
                p.setConditionDesc(rs.getString("condition_desc"));
                p.setAdmittedOn(String.valueOf(rs.getDate("admitted_on")));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
