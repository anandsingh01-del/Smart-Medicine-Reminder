package com.project.main;

import com.project.ui.LoginUI;
import com.project.service.ReminderService; // ✅ ADD THIS

public class Main {

    public static void main(String[] args) {

        // 🔥 Start reminder (background)
        ReminderService.startReminder();

        // Start app
        new LoginUI();
    }
}