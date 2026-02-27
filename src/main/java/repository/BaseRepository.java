package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public abstract class BaseRepository {
    private static final String CONNECTION_ERROR_MSG = "Error connecting to database";
    protected DataSource dataSource;

    protected BaseRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected boolean exists(Integer id, String sql, String errorMessage) {
        try (Connection connection = dataSource.getConnection();
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

    protected void doTransactional(TransactionalVoidOperation operation, String message) {
        try (Connection connection = dataSource.getConnection()) {
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
    protected <T> T doTransactional(TransactionalOperation<T> operation, String message) {
        try (Connection connection = dataSource.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T result = operation.execute(connection);
                connection.commit();
                return result;
            } catch (Exception e) {
                connection.rollback();
                throw new RuntimeException(message, e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(CONNECTION_ERROR_MSG, e);
        }
    }
}