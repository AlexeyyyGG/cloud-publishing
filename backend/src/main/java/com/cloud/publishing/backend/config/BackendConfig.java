package com.cloud.publishing.backend.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {
        "com.cloud.publishing.backend.service",
        "com.cloud.publishing.backend.repository",
        "com.cloud.publishing.backend.security",
        "com.cloud.publishing.backend.config",
        "com.cloud.publishing.backend.mapper",
        "com.cloud.publishing.backend.controller"
})
public class BackendConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource(DbProperties dbProperties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbProperties.getUrl());
        config.setUsername(dbProperties.getUser());
        config.setPassword(dbProperties.getPassword());
        config.setInitializationFailTimeout(dbProperties.getInitializationFailTimeout());
        config.addDataSourceProperty("cachePrepStmts", dbProperties.isCachePrepStmts());
        config.addDataSourceProperty("prepStmtCacheSize", dbProperties.getPrepStmtCacheSize());
        config.addDataSourceProperty("prepStmtCacheSqlLimit",
                dbProperties.getPrepStmtCacheSqlLimit());
        return new HikariDataSource(config);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}