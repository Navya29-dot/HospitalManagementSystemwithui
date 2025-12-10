package com.hms.dao;

import com.hms.db.DBConnection;
import com.hms.model.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // Book / create appointment (Staff use karega)
    public boolean addAppointment(Appointment a) {
        boolean success = false;

        String sql = "INSERT INTO appointments(patient_id, doctor_id, appointment_datetime, " +
                "status, description) VALUES (?,?,?,?,?)";


        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, a.getPatientId());
            ps.setInt(2, a.getDoctorId());
            ps.setString(3, a.getAppointmentDatetime());
            ps.setString(4, a.getStatus());
            ps.setString(5, a.getDescription());
            success = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    // Doctor ke liye uske saare appointments
    public List<Appointment> getAppointmentsForDoctor(int doctorId) {
        List<Appointment> list = new ArrayList<>();

        String sql = "SELECT * FROM appointments WHERE doctor_id = ? ORDER BY appointment_datetime";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment a = new Appointment();
                a.setAppointmentId(rs.getInt("appointment_id"));
                a.setPatientId(rs.getInt("patient_id"));
                a.setDoctorId(rs.getInt("doctor_id"));
                a.setAppointmentDatetime(String.valueOf(rs.getTimestamp("appointment_datetime")));
                a.setStatus(rs.getString("status"));
                a.setDescription(rs.getString("description"));
                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Dashboard ke liye "appointments today" count simple method
    public int countAppointmentsToday() {
        int count = 0;

        String sql = "SELECT COUNT(*) FROM appointments " +
                "WHERE DATE(appointment_datetime) = CURDATE()";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();

        String sql = "SELECT * FROM appointments ORDER BY appointment_datetime DESC";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment a = new Appointment();
                a.setAppointmentId(rs.getInt("appointment_id"));
                a.setPatientId(rs.getInt("patient_id"));
                a.setDoctorId(rs.getInt("doctor_id"));
                a.setAppointmentDatetime(String.valueOf(rs.getTimestamp("appointment_datetime")));
                a.setStatus(rs.getString("status"));
                a.setDescription(rs.getString("description"));
                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
