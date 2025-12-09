package com.hms;

import com.hms.dao.UserDAO;
import com.hms.model.User;

public class TestBackend {
    public static void main(String[] args) {

        UserDAO dao = new UserDAO();
        User u = dao.login("admin", "admin123");

        if (u != null) {
            System.out.println("Login OK");
            System.out.println("Logged in as: " + u.getUsername() + " | Role = " + u.getRoleId());
        } else {
            System.out.println("Invalid login");
        }
    }
}
