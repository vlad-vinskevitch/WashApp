package com.sharkit.stft.ui;

import java.util.List;

public class StaticAdmin {
    private static String name, password, role, email;
    private static List<String> tag;

    public static List<String> getTag() {
        return tag;
    }

    public static void setTag(List<String> tag) {
        StaticAdmin.tag = tag;
    }

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
