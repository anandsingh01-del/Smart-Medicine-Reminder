package com.project.service;

import javax.swing.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.*;

public class ReminderService {

    private static boolean started = false;
    private static String lastTriggered = "";

    public static void startReminder() {

        if (started) return; // prevent multiple threads
        started = true;

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        Runnable task = () -> {

            try {
                // current time in HH:mm
                String now = LocalTime.now()
                        .format(DateTimeFormatter.ofPattern("HH:mm"));

                System.out.println("NOW: " + now); // DEBUG

                ArrayList<String> medicines = MedicineService.getMedicines();

                for (String med : medicines) {

                    // correct split
                    String[] parts = med.split(" \\| ");

                    if (parts.length < 3) continue; // safety

                    String name = parts[1];
                    String time = parts[2].trim();

                    System.out.println("Checking: " + name + " at " + time);

                    // match time and prevent duplicate popup
                    if (now.equals(time) && !time.equals(lastTriggered)) {

                        lastTriggered = time;

                        SwingUtilities.invokeLater(() ->
                                JOptionPane.showMessageDialog(null,
                                        "⏰ Time to take medicine!\n" + name)
                        );
                    }
                }

            } catch (Exception e) {
                System.out.println("Reminder Error: " + e.getMessage());
            }
        };

        // run every 10 seconds for better accuracy
        scheduler.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS);
    }
}