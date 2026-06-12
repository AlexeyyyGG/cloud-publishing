package com.cloud.publishing.backend.repository.implementation;

import com.cloud.publishing.backend.repository.TransactionalOperation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;

public abstract class BaseRepository {
    private static final String CONNECTION_ERROR_MSG = "Ошибка подключения к БД";
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

    protected Map<Integer, Set<Integer>> loadRelations(
            Connection connection,
            String sql,
            String firstColumn,
            String secondColumn
    ) throws SQLException {
        Map<Integer, Set<Integer>> relations = new HashMap<>();
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int firstId = resultSet.getInt(firstColumn);
                int secondId = resultSet.getInt(secondColumn);
                relations.computeIfAbsent(firstId, k -> new HashSet<>()).add(secondId);
            }
        }
        return relations;
    }
}