package com.project.ui;

import com.project.service.HealthService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ViewHealthUI {

    public ViewHealthUI() {

        JFrame frame = new JFrame("Health Records");
        frame.setSize(550, 350);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        // 🔹 Table setup
        String[] columns = {"BP", "Sugar", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 500, 220);
        frame.add(scrollPane);

        // 🔄 Refresh Button
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setBounds(200, 260, 120, 30);
        frame.add(refreshBtn);

        // 🔥 Load Data Method
        Runnable loadData = () -> {
            model.setRowCount(0); // clear old data

            ArrayList<String> data = HealthService.getHealthData();

            if (data.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No health records found!");
                return;
            }

            for (String record : data) {
                String[] parts = record.split(" \\| ");
                model.addRow(parts);
            }
        };

        // Initial load
        loadData.run();

        // Refresh action
        refreshBtn.addActionListener(e -> loadData.run());

        frame.setVisible(true);
    }
}