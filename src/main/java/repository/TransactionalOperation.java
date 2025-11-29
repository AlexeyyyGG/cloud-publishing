package repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface TransactionalOperation {
    void execute(Connection connection) throws SQLException;
}