package com.sharkit.stft.ui;

public class StaticAdmin {
    private static String name, password, role, email;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        StaticAdmin.name = name;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        StaticAdmin.password = password;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        StaticAdmin.role = role;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        StaticAdmin.email = email;
    }
}
