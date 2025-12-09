package com.hms.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {


    private static final String URL  = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root";
    private static final String PASS = "prachi00";

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
