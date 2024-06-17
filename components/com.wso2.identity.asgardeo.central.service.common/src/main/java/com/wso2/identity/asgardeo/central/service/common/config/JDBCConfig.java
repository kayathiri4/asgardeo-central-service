/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.common.config;

public interface JDBCConfig {
    /**
     * Get the username of the database.
     * @return Username of the database.
     */
    String getUsername();

    /**
     * Set the username of the database.
     * @param username Username of the database.
     */
    void setUsername(String username);

    /**
     * Get the password of the database.
     * @return Password of the database.
     */
    String getPassword();

    /**
     * Set the password of the database.
     * @param password Password of the database.
     */
    void setPassword(String password);

    /**
     * Get the password file of the database.
     * @return Password file of the database.
     */
    String getPasswordFile();

    /**
     * Set the password file of the database.
     * @param passwordFile Password file of the database.
     */
    void setPasswordFile(String passwordFile);

    /**
     * Get the URL of the database.
     * @return URL of the database.
     */
    String getUrl();

    /**
     * Set the URL of the database.
     * @param url URL of the database.
     */
    void setUrl(String url);

    /**
     * Get the driver class name of the database.
     * @return Driver class name of the database.
     */
    String getDriverClassName();

    /**
     * Set the driver class name of the database.
     * @param driverClassName Driver class name of the database.
     */
    void setDriverClassName(String driverClassName);

    /**
     * Get the validation query of the database.
     * @return Validation query of the database.
     */
    String getValidationQuery();

    /**
     * Set the validation query of the database.
     * @param validationQuery Validation query of the database.
     */
    void setValidationQuery(String validationQuery);

    /**
     * Get the test on borrow status of the database.
     * @return Test on borrow status of the database.
     */
    boolean isTestOnBorrow();

    /**
     * Set the test on borrow status of the database.
     * @param testOnBorrow Test on borrow status of the database.
     */
    void setTestOnBorrow(boolean testOnBorrow);

    /**
     * Get the max active connection count of the database.
     * @return Max active connection count of the database.
     */
    int getMaxActive();

    /**
     * Set the max active connection count of the database.
     * @param maxActive Max active connection count of the database.
     */
    void setMaxActive(int maxActive);

    /**
     * Get the max waiting time of the database.
     * @return Max waiting time of the database.
     */
    long getMaxWait();

    /**
     * Set the max waiting time of the database.
     * @param maxWait Max waiting time of the database.
     */
    void setMaxWait(long maxWait);

    /**
     * Get the min idle connection timeout of the database.
     * @return Min idle connection timeout of the database.
     */
    int getMinIdle();

    /**
     * Set the min idle connection timeout of the database.
     * @param minIdle Min idle connection timeout of the database.
     */
    void setMinIdle(int minIdle);

    /**
     * Output all the configuration values as a string sanitizing sensitive information.
     * @return String representation of the configurations.
     */
    String toString();

}
