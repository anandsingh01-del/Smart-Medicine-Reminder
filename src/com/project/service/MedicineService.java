package com.project.service;

import com.project.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class MedicineService {

    // ✅ ADD MEDICINE
    public static boolean addMedicine(String name, String time, int days) {
        try (Connection conn = DBConnection.getConnection()) {

            String sql = "INSERT INTO medicines(user_id, name, time, days) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, Session.currentUserId); // 🔥 user-specific
            ps.setString(2, name);
            ps.setString(3, time);
            ps.setInt(4, days);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Add Medicine Error: " + e.getMessage());
            return false;
        }
    }

    // ✅ GET MEDICINES (USER-SPECIFIC + STATUS INCLUDED)
    public static ArrayList<String> getMedicines() {
        ArrayList<String> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {

            String sql = "SELECT * FROM medicines WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, Session.currentUserId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String data = rs.getInt("id") + " | "
                        + rs.getString("name") + " | "
                        + rs.getString("time") + " | "
                        + rs.getInt("days") + " | "
                        + rs.getString("status"); // 🔥 IMPORTANT

                list.add(data);
            }

        } catch (Exception e) {
            System.out.println("Fetch Error: " + e.getMessage());
        }

        return list;
    }

    // ✅ DELETE MEDICINE (USER SAFE)
    public static boolean deleteMedicine(int id) {
        try (Connection conn = DBConnection.getConnection()) {

            String sql = "DELETE FROM medicines WHERE id = ? AND user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setInt(2, Session.currentUserId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            System.out.println("Delete Error: " + e.getMessage());
            return false;
        }
    }

    // ✅ MARK AS TAKEN
    public static boolean markTaken(int id) {
        return updateStatus(id, "taken");
    }

    // ✅ MARK AS MISSED
    public static boolean markMissed(int id) {
        return updateStatus(id, "missed");
    }

    // 🔧 COMMON STATUS UPDATE METHOD
    private static boolean updateStatus(int id, String status) {
        try (Connection conn = DBConnection.getConnection()) {

            String sql = "UPDATE medicines SET status = ? WHERE id = ? AND user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, id);
            ps.setInt(3, Session.currentUserId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            System.out.println("Update Error: " + e.getMessage());
            return false;
        }
    }

    // ✅ COMPLIANCE CALCULATION
    public static double getCompliance() {
        try (Connection conn = DBConnection.getConnection()) {

            // total medicines
            String totalSql = "SELECT COUNT(*) FROM medicines WHERE user_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(totalSql);
            ps1.setInt(1, Session.currentUserId);
            ResultSet rs1 = ps1.executeQuery();

            int total = 0;
            if (rs1.next()) {
                total = rs1.getInt(1);
            }

            // taken medicines
            String takenSql = "SELECT COUNT(*) FROM medicines WHERE user_id = ? AND status = 'taken'";
            PreparedStatement ps2 = conn.prepareStatement(takenSql);
            ps2.setInt(1, Session.currentUserId);
            ResultSet rs2 = ps2.executeQuery();

            int taken = 0;
            if (rs2.next()) {
                taken = rs2.getInt(1);
            }

            if (total == 0) return 0;

            return (taken * 100.0) / total;

        } catch (Exception e) {
            System.out.println("Compliance Error: " + e.getMessage());
            return 0;
        }
    }
}