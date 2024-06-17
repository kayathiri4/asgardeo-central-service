/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.common.builder;

import com.wso2.identity.asgardeo.central.service.common.config.JDBCConfig;
import com.wso2.identity.asgardeo.central.service.common.exception.DatasourceException;
import com.wso2.identity.asgardeo.central.service.common.util.PasswordManager;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorCodes.ERROR_CODE_CEN_SC_10001;
import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorCodes.ERROR_CODE_CEN_SC_10002;

/**
 * <b>JDBC data source builder.</b>
 * <p>
 * This utility class is used to build data sources related JDBC connections.
 * </p>
 *
 * @version 1.0.0
 * @see JDBCConfig
 * @see org.apache.commons.dbcp2.BasicDataSource
 * @since 1.0.0
 */
public class JDBCDataSourceBuilder {

    private static final Log LOG = LogFactory.getLog(JDBCDataSourceBuilder.class);

    /**
     * Build a data source using the given JDBC configuration.
     *
     * @param config {@link JDBCConfig JDBCConfig}
     * @return {@link org.apache.commons.dbcp2.BasicDataSource BasicDataSource}
     * @throws DatasourceException If an error occurs while building the data source.
     *                             Including config validation errors.
     */
    public BasicDataSource build(JDBCConfig config) throws DatasourceException {

        if (config == null) {
            throw new DatasourceException("JDBC configuration is missing.", ERROR_CODE_CEN_SC_10001);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Building data source with the following configuration: " + config.toString());
        }

        // Validate for mandatory configurations.
        if (StringUtils.isBlank(config.getUrl()) || StringUtils.isBlank(config.getUsername()) ||
                (StringUtils.isBlank(config.getPassword()) && StringUtils.isBlank(config.getPasswordFile()))) {
            throw new DatasourceException("One or more mandatory JDBC configurations are missing. Enable debug logs " +
                    "to see the configuration details.", ERROR_CODE_CEN_SC_10002);
        }

        // If the password is from a file, read from it. Otherwise, use the password from the config.
        String password = config.getPassword();
        if (StringUtils.isNotBlank(config.getPasswordFile())) {
            password = PasswordManager.getPasswordFromFile(config.getPasswordFile());
        }

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(config.getUrl());
        basicDataSource.setUsername(config.getUsername());
        basicDataSource.setPassword(password);
        basicDataSource.setDriverClassName(config.getDriverClassName());
        basicDataSource.setValidationQuery(config.getValidationQuery());
        basicDataSource.setTestOnBorrow(config.isTestOnBorrow());
        basicDataSource.setMaxTotal(config.getMaxActive());
        basicDataSource.setMaxWaitMillis(config.getMaxWait());
        basicDataSource.setMinIdle(config.getMinIdle());

        return basicDataSource;
    }
}
