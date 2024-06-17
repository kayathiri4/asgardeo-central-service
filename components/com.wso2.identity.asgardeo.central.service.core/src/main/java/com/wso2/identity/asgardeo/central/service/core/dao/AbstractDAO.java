package com.wso2.identity.asgardeo.central.service.core.dao;

import com.wso2.identity.asgardeo.central.service.common.builder.JDBCDataSourceBuilder;
import com.wso2.identity.asgardeo.central.service.common.config.JDBCConfig;
import com.wso2.identity.asgardeo.central.service.common.exception.DatasourceException;
import org.wso2.carbon.database.utils.jdbc.JdbcTemplate;
import org.wso2.carbon.database.utils.jdbc.NamedJdbcTemplate;

public class AbstractDAO {

    private final JDBCConfig jdbcConfig;

    /**
     * Constructor for the {@link AbstractDAO}.
     * @param jdbcConfig JDBC configuration.
     * @see JDBCConfig
     */
    public AbstractDAO(JDBCConfig jdbcConfig) {

        this.jdbcConfig = jdbcConfig;
    }

    /**
     * Get the JDBC configuration.
     * @return JDBC configuration.
     * @see JDBCConfig
     */
    public JDBCConfig getJDBCConfig() {
        return jdbcConfig;
    }

    /**
     * Get a new {@link JdbcTemplate} instance.
     * @return {@link JdbcTemplate} instance.
     * @throws DatasourceException If an error occurred while creating the {@link JdbcTemplate} instance.
     */
    public NamedJdbcTemplate getNewTemplate() throws DatasourceException {

        JDBCDataSourceBuilder jdbcDataSourceBuilder = new JDBCDataSourceBuilder();
        return new NamedJdbcTemplate(jdbcDataSourceBuilder.build(getJDBCConfig()));
    }
}
