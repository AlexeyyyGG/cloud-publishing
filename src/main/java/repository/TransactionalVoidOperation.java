package repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface TransactionalVoidOperation {
    void execute(Connection connection) throws SQLException;
}