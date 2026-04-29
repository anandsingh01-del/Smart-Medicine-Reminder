package com.project.service;

import com.project.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class HealthService {

    // ✅ ADD HEALTH DATA
    public static boolean addHealth(String bp, String sugar, String date) {
        try (Connection conn = DBConnection.getConnection()) {

            String sql = "INSERT INTO health_data(bp, sugar, date, user_id) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, bp);
            ps.setString(2, sugar);
            ps.setString(3, date);
            ps.setInt(4, Session.currentUserId);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Health Error: " + e.getMessage());
            return false;
        }
    }

    // ✅ FETCH HEALTH DATA
    public static ArrayList<String> getHealthData() {
        ArrayList<String> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {

            String sql = "SELECT * FROM health_data WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Session.currentUserId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String data = rs.getString("bp") + " | "
                        + rs.getString("sugar") + " | "
                        + rs.getString("date");

                list.add(data);
            }

        } catch (Exception e) {
            System.out.println("Fetch Health Error: " + e.getMessage());
        }

        return list;
    }

    // 📊 AVERAGE SUGAR
    public static int getAverageSugar() {
        try (Connection conn = DBConnection.getConnection()) {

            String sql = "SELECT AVG(CAST(sugar AS SIGNED)) FROM health_data WHERE user_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Session.currentUserId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Avg Error: " + e.getMessage());
        }

        return 0;
    }

    // 📊 TOTAL RECORDS
    public static int getTotalRecords() {
        try (Connection conn = DBConnection.getConnection()) {

            String sql = "SELECT COUNT(*) FROM health_data WHERE user_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Session.currentUserId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Count Error: " + e.getMessage());
        }

        return 0;
    }
}