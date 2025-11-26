package repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import config.DbProperties;
import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnection {
    private static final HikariConfig config = new HikariConfig();
    private final HikariDataSource dataSource;

    public DatabaseConnection(DbProperties dbProperties) {
        config.setJdbcUrl(dbProperties.getUrl());
        config.setUsername(dbProperties.getUser());
        config.setPassword(dbProperties.getPassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}