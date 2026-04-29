package com.project.service;

import com.project.db.DBConnection;
import java.sql.*;

public class UserService {

    // REGISTER
    public static boolean register(String name, String email, String password) {
        try {
            Connection conn = DBConnection.getConnection();

            // Check if user already exists
            String checkQuery = "SELECT * FROM users WHERE email=?";
            PreparedStatement checkPs = conn.prepareStatement(checkQuery);
            checkPs.setString(1, email.trim());

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                return false; // user exists
            }

            // Insert new user
            String insertQuery = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertQuery);

            ps.setString(1, name.trim());
            ps.setString(2, email.trim());
            ps.setString(3, password.trim());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Register Error: " + e.getMessage());
            return false;
        }
    }

    // LOGIN
    public static int login(String email, String password) {
        try {
            Connection conn = DBConnection.getConnection();

            String query = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, email.trim());
            ps.setString(2, password.trim());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id"); // return userId
            }

        } catch (Exception e) {
            System.out.println("Login Error: " + e.getMessage());
        }
        return -1;
    }
}