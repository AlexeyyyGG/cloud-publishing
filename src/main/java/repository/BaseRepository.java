package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseRepository {
    private static final String CONNECTION_ERROR_MSG = "Error connecting to database";
    protected DatabaseConnection databaseConnection;

    protected BaseRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public boolean exists(Integer id, String sql, String errorMessage) {
        try (Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(errorMessage, e);
        }
    }

    public void doTransactional(TransactionalOperation operation, String message) {
        try (Connection connection = databaseConnection.getConnection()) {
            try {
                connection.setAutoCommit(false);
                operation.execute(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(message, e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(CONNECTION_ERROR_MSG, e);
        }
    }

}