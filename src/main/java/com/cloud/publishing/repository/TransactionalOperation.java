package com.cloud.publishing.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface TransactionalOperation<T> {
    T execute(Connection connection) throws SQLException;
}