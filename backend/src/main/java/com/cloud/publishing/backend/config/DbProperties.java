package com.cloud.publishing.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DbProperties {
    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String user;

    @Value("${db.password}")
    private String password;

    @Value("${db.cachePrepStmts}")
    private boolean cachePrepStmts;

    @Value("${db.prepStmtCacheSize}")
    private int prepStmtCacheSize;

    @Value("${db.prepStmtCacheSqlLimit}")
    private int prepStmtCacheSqlLimit;

    @Value("${db.initializationFailTimeout}")
    private long initializationFailTimeout;

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public boolean isCachePrepStmts() {
        return cachePrepStmts;
    }

    public int getPrepStmtCacheSize() {
        return prepStmtCacheSize;
    }

    public int getPrepStmtCacheSqlLimit() {
        return prepStmtCacheSqlLimit;
    }

    public long getInitializationFailTimeout() {
        return initializationFailTimeout;
    }
}