package repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static final String PROPERTIES_PATH = "/db/db.properties";
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final Properties properties = new Properties();
    private static final String FAILED_TO_LOAD_MESSAGE = "Failed to load database configuration";
    private static final String DRIVER_NOT_FOUND_MESSAGE = "MySQL JDBC Driver not found";
    private static final String FAILED_TO_CONNECT_MESSAGE = "Failed to connect to database";

    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(DRIVER_NOT_FOUND_MESSAGE, e);
        }
        try (InputStream input = DatabaseConnection.class.getResourceAsStream(PROPERTIES_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(FAILED_TO_LOAD_MESSAGE, e);
        }
    }

    public static Connection getConnection() {
        String url = properties.getProperty(DB_URL);
        String user = properties.getProperty(DB_USER);
        String password = properties.getProperty(DB_PASSWORD);
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_CONNECT_MESSAGE, e);
        }
    }
}