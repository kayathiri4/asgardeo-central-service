package com.wso2.identity.asgardeo.central.service.core.config;

import com.wso2.identity.asgardeo.central.service.common.config.JDBCConfig;

public class CentralServiceJDBCConfig implements JDBCConfig {

    private String username;
    private String password;
    private String passwordFile;
    private String url;
    private String driverClassName;
    private String validationQuery;
    private boolean testOnBorrow;
    private int maxActive;
    private long maxWait;
    private int minIdle;

    @Override
    public String getUsername() {

        return username;
    }

    @Override
    public void setUsername(String username) {

        this.username = username;
    }

    @Override
    public String getPassword() {

        return password;
    }

    @Override
    public void setPassword(String password) {

        this.password = password;
    }

    @Override
    public String getPasswordFile() {

        return passwordFile;
    }

    @Override
    public void setPasswordFile(String passwordFile) {

        this.passwordFile = passwordFile;
    }

    @Override
    public String getUrl() {

        return url;
    }

    @Override
    public void setUrl(String url) {

        this.url = url;
    }

    @Override
    public String getDriverClassName() {

        return driverClassName;
    }

    @Override
    public void setDriverClassName(String driverClassName) {

        this.driverClassName = driverClassName;
    }

    @Override
    public String getValidationQuery() {

        return validationQuery;
    }

    @Override
    public void setValidationQuery(String validationQuery) {

        this.validationQuery = validationQuery;
    }

    @Override
    public boolean isTestOnBorrow() {

        return testOnBorrow;
    }

    @Override
    public void setTestOnBorrow(boolean testOnBorrow) {

        this.testOnBorrow = testOnBorrow;
    }

    @Override
    public int getMaxActive() {

        return maxActive;
    }

    @Override
    public void setMaxActive(int maxActive) {

        this.maxActive = maxActive;
    }

    @Override
    public long getMaxWait() {

        return maxWait;
    }

    @Override
    public void setMaxWait(long maxWait) {

        this.maxWait = maxWait;
    }

    @Override
    public int getMinIdle() {

        return minIdle;
    }

    @Override
    public void setMinIdle(int minIdle) {

        this.minIdle = minIdle;
    }
}
