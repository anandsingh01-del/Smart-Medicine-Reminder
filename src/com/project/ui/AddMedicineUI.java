package com.project.ui;

import com.project.service.MedicineService;

import javax.swing.*;

public class AddMedicineUI {

    public AddMedicineUI() {
        JFrame frame = new JFrame("Add Medicine");
        frame.setSize(300, 250);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JLabel nameLabel = new JLabel("Medicine:");
        nameLabel.setBounds(20, 20, 100, 25);
        frame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(120, 20, 140, 25);
        frame.add(nameField);

        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setBounds(20, 60, 100, 25);
        frame.add(timeLabel);

        JTextField timeField = new JTextField();
        timeField.setBounds(120, 60, 140, 25);
        frame.add(timeField);

        JLabel daysLabel = new JLabel("Days:");
        daysLabel.setBounds(20, 100, 100, 25);
        frame.add(daysLabel);

        JTextField daysField = new JTextField();
        daysField.setBounds(120, 100, 140, 25);
        frame.add(daysField);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(90, 150, 100, 30);
        frame.add(saveBtn);

        // ✅ SAVE ACTION
        saveBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String time = timeField.getText().trim();
                int days = Integer.parseInt(daysField.getText().trim());

                if (name.isEmpty() || time.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                    return;
                }

                boolean success = MedicineService.addMedicine(name, time, days);

                if (success) {
                    JOptionPane.showMessageDialog(frame, "Medicine Saved!");

                    // clear fields after save
                    nameField.setText("");
                    timeField.setText("");
                    daysField.setText("");

                } else {
                    JOptionPane.showMessageDialog(frame, "Error saving medicine!");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Days must be a number!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Something went wrong!");
            }
        });

        frame.setVisible(true);
    }
}