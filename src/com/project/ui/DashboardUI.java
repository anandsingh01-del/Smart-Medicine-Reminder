package com.project.ui;

import com.project.service.MedicineService;
import com.project.service.HealthService;
import com.project.service.Session;

import javax.swing.*;
import java.awt.*;

public class DashboardUI {

    JLabel complianceLabel;
    JLabel avgSugarLabel;
    JLabel totalHealthLabel;

    public DashboardUI() {

        JFrame frame = new JFrame("Dashboard");

        // 🔥 Prevent app from closing (for background reminder)
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 🔹 TITLE
        JLabel title = new JLabel("Welcome to Dashboard", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        // 🔥 COMPLIANCE
        gbc.gridy = 1;
        complianceLabel = new JLabel("", JLabel.CENTER);
        complianceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(complianceLabel, gbc);

        // 📊 AVG SUGAR
        gbc.gridy = 2;
        avgSugarLabel = new JLabel("", JLabel.CENTER);
        panel.add(avgSugarLabel, gbc);

        // 📊 TOTAL RECORDS
        gbc.gridy = 3;
        totalHealthLabel = new JLabel("", JLabel.CENTER);
        panel.add(totalHealthLabel, gbc);

        updateDashboard();

        // 🔄 REFRESH BUTTON
        gbc.gridy = 4;
        JButton refreshBtn = new JButton("Refresh");
        panel.add(refreshBtn, gbc);
        refreshBtn.addActionListener(e -> updateDashboard());

        // ➕ ADD MEDICINE
        gbc.gridy = 5;
        JButton addMedicineBtn = new JButton("Add Medicine");
        panel.add(addMedicineBtn, gbc);
        addMedicineBtn.addActionListener(e -> new AddMedicineUI());

        // 📋 VIEW MEDICINES
        gbc.gridy = 6;
        JButton viewMedicineBtn = new JButton("View Medicines");
        panel.add(viewMedicineBtn, gbc);
        viewMedicineBtn.addActionListener(e -> new ViewMedicineUI());

        // ❤️ HEALTH DATA
        gbc.gridy = 7;
        JButton healthBtn = new JButton("Add Health Data");
        panel.add(healthBtn, gbc);
        healthBtn.addActionListener(e -> new HealthUI());

        // 📊 VIEW HEALTH
        gbc.gridy = 8;
        JButton viewHealthBtn = new JButton("View Health Data");
        panel.add(viewHealthBtn, gbc);
        viewHealthBtn.addActionListener(e -> new ViewHealthUI());

        // 🔴 LOGOUT
        gbc.gridy = 9;
        JButton logoutBtn = new JButton("Logout");
        panel.add(logoutBtn, gbc);

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                Session.logout();
                frame.dispose();
                new LoginUI();
            }
        });

        // 🚀 SYSTEM TRAY (BACKGROUND MODE)
        if (SystemTray.isSupported()) {

            SystemTray tray = SystemTray.getSystemTray();

            // ⚠️ You can replace with icon.png later
            Image image = Toolkit.getDefaultToolkit().createImage("");

            PopupMenu popup = new PopupMenu();

            MenuItem openItem = new MenuItem("Open");
            MenuItem exitItem = new MenuItem("Exit");

            popup.add(openItem);
            popup.add(exitItem);

            TrayIcon trayIcon = new TrayIcon(image, "Medicine Reminder", popup);
            trayIcon.setImageAutoSize(true);

            try {
                tray.add(trayIcon);
            } catch (Exception e) {
                System.out.println("Tray Error: " + e.getMessage());
            }

            // 🔹 Open app
            openItem.addActionListener(e -> {
                frame.setVisible(true);
                frame.setExtendedState(JFrame.NORMAL);
            });

            // 🔹 Exit app completely
            exitItem.addActionListener(e -> {
                tray.remove(trayIcon);
                System.exit(0);
            });

            // 🔥 Close → minimize to tray (NO POPUP)
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) {
                    frame.setVisible(false); // silent background
                }
            });

            // ✅ Optional: show tray notification once
            trayIcon.displayMessage(
                    "Medicine Tracker",
                    "Running in background",
                    TrayIcon.MessageType.INFO
            );
        }

        frame.add(panel);
        frame.setVisible(true);
    }

    private void updateDashboard() {

        double compliance = MedicineService.getCompliance();
        complianceLabel.setText("Compliance: " + String.format("%.2f", compliance) + "%");

        int avgSugar = HealthService.getAverageSugar();
        avgSugarLabel.setText("Average Sugar: " + avgSugar);

        int total = HealthService.getTotalRecords();
        totalHealthLabel.setText("Health Records: " + total);
    }
}