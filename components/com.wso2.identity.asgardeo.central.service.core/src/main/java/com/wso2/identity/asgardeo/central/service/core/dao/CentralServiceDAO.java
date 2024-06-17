package com.wso2.identity.asgardeo.central.service.core.dao;

import com.wso2.identity.asgardeo.central.service.common.config.JDBCConfig;
import com.wso2.identity.asgardeo.central.service.common.exception.DatasourceException;
import com.wso2.identity.asgardeo.central.service.core.constant.Constants;
import org.wso2.carbon.database.utils.jdbc.NamedJdbcTemplate;
import org.wso2.carbon.database.utils.jdbc.exceptions.DataAccessException;

import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorCodes.ERROR_CODE_CEN_SC_50001;

public class CentralServiceDAO extends AbstractDAO {

    /**
     * Constructor for the {@link AbstractDAO}.
     *
     * @param jdbcConfig JDBC configuration.
     */
    public CentralServiceDAO(JDBCConfig jdbcConfig) {

        super(jdbcConfig);
    }

    public void addTenant(String tenantUUID, String tenantDomain, String userName, String cluster)
            throws DatasourceException {

        NamedJdbcTemplate jdbcTemplate = getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(Constants.SQLQueries.ADD_TENANT, preparedStatement -> {
                preparedStatement.setString("UUID", tenantUUID);
                preparedStatement.setString("tenantDomain", tenantDomain);
                preparedStatement.setString("username", userName);
                preparedStatement.setString("cluster", cluster);
            });
        } catch (DataAccessException e) {
            throw new DatasourceException(String.format("Error while adding the tenant %s.", tenantDomain),
                    ERROR_CODE_CEN_SC_50001, e);
        }
    }

    public String getTenant(String tenantDomain) throws DatasourceException {

        String tenantId = null;
        try {
            NamedJdbcTemplate jdbcTemplate = getNewTemplate();
            tenantId = jdbcTemplate.fetchSingleRecord(Constants.SQLQueries.GET_TENANT, (resultSet, rowNumber) -> {
                return resultSet.getString("UUID");
            }, preparedStatement -> {
                preparedStatement.setString("tenantDomain", tenantDomain);
            });
        } catch (DataAccessException e) {
            throw new DatasourceException(String.format("Error while getting the tenant %s.", tenantDomain),
                    ERROR_CODE_CEN_SC_50001, e);
        }
        return tenantId;
    }

    public void deleteTenant(String tenantId) {

        try {
            NamedJdbcTemplate jdbcTemplate = getNewTemplate();
            jdbcTemplate.executeUpdate(Constants.SQLQueries.DELETE_TENANT, preparedStatement -> {
                preparedStatement.setString("UUID", tenantId);
            });
        } catch (DatasourceException | DataAccessException e) {

            throw new RuntimeException(e);
        }

    }

}
