package org.example.texteditor.db;

import java.sql.*;


public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=text_editor;encrypt=false;";
    private static final String USER = "Cucumber";
    private static final String PASSWORD = "1657udte";

    private Connection connection;

    public DatabaseConnection() {
        connect();
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Підключено до бази даних");
        } catch (SQLException e) {
            System.err.println("❌ Помилка підключення: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
