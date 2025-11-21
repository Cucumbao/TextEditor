package org.example.texteditor.db;

import org.example.texteditor.model.File;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<File> getAllFiles() {
        List<File> files = new ArrayList<>();
        String sql = "SELECT * FROM files";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                File f = new File();
                f.setId(rs.getLong("id"));
                f.setFileName(rs.getString("fileName"));
                f.setFilePath(rs.getString("link"));
                f.setContent(rs.getString("contents"));
                f.setUser(rs.getLong("user_id"));
                f.setLastUpdate(
                        rs.getDate("lastUpdate") != null
                                ? rs.getDate("lastUpdate").toString()
                                : "немає даних"
                );
                files.add(f);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return files;
    }


    public File getFileById(Long id) {
        String sql = "SELECT * FROM Files WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                File f = new File();
                f.setId(rs.getLong("id"));
                f.setFileName(rs.getString("fileName"));
                f.setFilePath(rs.getString("link"));
                f.setContent(rs.getString("contents"));
                f.setUser(rs.getLong("user_id"));
                Date lastUpdate = rs.getDate("lastUpdate");
                f.setLastUpdate(lastUpdate != null ? lastUpdate.toString() : "немає даних");
                return f;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveFile(File file) {
        if (file.getId() != null && getFileById(file.getId()) != null)
            updateFile(file);
        else
            insertFile(file); // тепер процедура
    }

    public void insertFile(File file) {
        String call = "{call InsertFile(?, ?, ?, ?, ?)}";
        try (CallableStatement cs = connection.prepareCall(call)) {
            cs.setString(1, file.getFileName());
            cs.setString(2, file.getFilePath());
            cs.setString(3, file.getContent());

            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
            cs.setDate(4, today);

            cs.setLong(5, file.getUser());

            cs.execute();
            System.out.println("✅ Файл додано через процедуру InsertFile");
        } catch (SQLException e) {
            System.err.println("❌ Помилка при виклику InsertFile: " + e.getMessage());
        }
    }
    // --- Оновити існуючий файл ---
    private void updateFile(File file) {
        String sql = """
            UPDATE Files 
            SET fileName=?, link=?, contents=?, user_id=?, lastUpdate=? 
            WHERE id=?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, file.getFileName());
            ps.setString(2, file.getFilePath());
            ps.setString(3, file.getContent());
            ps.setLong(4, file.getUser());
            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
            ps.setDate(5, today);
            ps.setLong(6, file.getId());
            ps.executeUpdate();
            System.out.println("♻️ Файл оновлено (id=" + file.getId() + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteFile(Long id) {
        String sql = "DELETE FROM Files WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
