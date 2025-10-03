package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DRIVER_NOT_FOUND_MSG = "MySQL JDBC Driver not found";
    private static final String FAILED_TO_CONNECT_MSG = "Failed to connect to database";

    public static Connection getConnection(String url, String user, String password) {
        try {
            Class.forName(DB_DRIVER);
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(DRIVER_NOT_FOUND_MSG, e);
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_CONNECT_MSG, e);
        }
    }
}