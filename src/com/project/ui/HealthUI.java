package com.project.ui;

import com.project.service.HealthService;

import javax.swing.*;

public class HealthUI {

    public HealthUI() {
        JFrame frame = new JFrame("Health Data");
        frame.setSize(350, 300);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        // 🔹 BP
        JLabel bpLabel = new JLabel("Blood Pressure:");
        bpLabel.setBounds(20, 20, 120, 25);
        frame.add(bpLabel);

        JTextField bpField = new JTextField();
        bpField.setBounds(150, 20, 150, 25);
        frame.add(bpField);

        // 🔹 Sugar
        JLabel sugarLabel = new JLabel("Sugar Level:");
        sugarLabel.setBounds(20, 60, 120, 25);
        frame.add(sugarLabel);

        JTextField sugarField = new JTextField();
        sugarField.setBounds(150, 60, 150, 25);
        frame.add(sugarField);

        // 🔹 Date
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setBounds(20, 100, 120, 25);
        frame.add(dateLabel);

        JTextField dateField = new JTextField();
        dateField.setBounds(150, 100, 150, 25);
        dateField.setText("YYYY-MM-DD"); // hint
        frame.add(dateField);

        // 🔹 Save Button
        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(110, 160, 100, 30);
        frame.add(saveBtn);

        // 🔥 SAVE LOGIC
        saveBtn.addActionListener(e -> {
            String bp = bpField.getText().trim();
            String sugar = sugarField.getText().trim();
            String date = dateField.getText().trim();

            if (bp.isEmpty() || sugar.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields required!");
                return;
            }

            boolean success = HealthService.addHealth(bp, sugar, date);

            if (success) {
                JOptionPane.showMessageDialog(frame, "Health Data Saved!");

                // clear fields
                bpField.setText("");
                sugarField.setText("");
                dateField.setText("YYYY-MM-DD");

            } else {
                JOptionPane.showMessageDialog(frame, "Error saving data!");
            }
        });

        frame.setVisible(true);
    }
}