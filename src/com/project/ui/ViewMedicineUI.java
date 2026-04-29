package com.project.ui;

import com.project.service.MedicineService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ViewMedicineUI {

    DefaultTableModel model;
    JTable table;

    public ViewMedicineUI() {

        JFrame frame = new JFrame("View Medicines");
        frame.setSize(650, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        // ✅ Updated columns (includes status)
        String[] columns = {"ID", "Name", "Time", "Days", "Status"};

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 600, 250);
        frame.add(scrollPane);

        // 🔴 DELETE BUTTON
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(50, 300, 100, 30);
        frame.add(deleteBtn);

        // 🟢 MARK TAKEN
        JButton takenBtn = new JButton("Mark Taken");
        takenBtn.setBounds(200, 300, 120, 30);
        frame.add(takenBtn);

        // 🔵 MARK MISSED
        JButton missedBtn = new JButton("Mark Missed");
        missedBtn.setBounds(380, 300, 120, 30);
        frame.add(missedBtn);

        loadData();

        // 🔥 DELETE
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(frame, "Select a row!");
                return;
            }

            int id = Integer.parseInt(model.getValueAt(row, 0).toString());

            if (MedicineService.deleteMedicine(id)) {
                model.removeRow(row);
                JOptionPane.showMessageDialog(frame, "Deleted!");
            } else {
                JOptionPane.showMessageDialog(frame, "Delete Failed!");
            }
        });

        // 🔥 MARK TAKEN
        takenBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(frame, "Select a row!");
                return;
            }

            int id = Integer.parseInt(model.getValueAt(row, 0).toString());

            if (MedicineService.markTaken(id)) {
                model.setValueAt("taken", row, 4);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed!");
            }
        });

        // 🔥 MARK MISSED
        missedBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(frame, "Select a row!");
                return;
            }

            int id = Integer.parseInt(model.getValueAt(row, 0).toString());

            if (MedicineService.markMissed(id)) {
                model.setValueAt("missed", row, 4);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed!");
            }
        });

        frame.setVisible(true);
    }

    private void loadData() {
        ArrayList<String> medicines = MedicineService.getMedicines();

        for (String med : medicines) {
            String[] parts = med.split(" \\| ");
            model.addRow(parts);
        }
    }
}