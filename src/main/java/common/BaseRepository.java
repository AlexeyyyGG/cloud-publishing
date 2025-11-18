package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseRepository {
    protected Connection connection;
    protected static final String FAILED_TO_CHECK_MESSAGE = "Failed to check if exists";
    private static final String SQL_EXIST = "SELECT EXISTS(SELECT 1 FROM employees WHERE id = ?)";

    protected BaseRepository(Connection connection) {
        this.connection = connection;
    }

    public boolean exists(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_EXIST)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_CHECK_MESSAGE, e);
        }
    }
}
