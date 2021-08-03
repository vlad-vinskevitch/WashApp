package com.sharkit.stft.ui;

public class Variable {
    private static String firm, driver, role, email;

    public static String getRole() {
        return role;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Variable.email = email;
    }

    public static void setRole(String role) {
        Variable.role = role;
    }

    public static String getDriver() {
        return driver;
    }

    public static void setDriver(String driver) {
        Variable.driver = driver;
    }

    public static String getFirm() {
        return firm;
    }

    public static void setFirm(String firm) {
        Variable.firm = firm;
    }
}
