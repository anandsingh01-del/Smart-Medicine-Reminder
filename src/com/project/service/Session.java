package com.project.service;

public class Session {

    public static int currentUserId = -1;

    // 🔥 Logout method
    public static void logout() {
        currentUserId = -1;
    }

    // 🔍 Optional: check if logged in
    public static boolean isLoggedIn() {
        return currentUserId != -1;
    }
}