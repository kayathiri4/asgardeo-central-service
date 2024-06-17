package com.wso2.identity.asgardeo.central.service.core;

import com.wso2.identity.asgardeo.central.service.common.config.JDBCConfig;
import com.wso2.identity.asgardeo.central.service.common.constant.ErrorMessage;
import com.wso2.identity.asgardeo.central.service.common.exception.CentralServiceClientException;
import com.wso2.identity.asgardeo.central.service.common.exception.CentralServiceException;
import com.wso2.identity.asgardeo.central.service.common.exception.DatasourceException;
import com.wso2.identity.asgardeo.central.service.core.dao.CentralServiceDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.annotations.Component;

import java.util.regex.Pattern;

import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorMessage.ERROR_CODE_EMPTY_TENANT_DOMAIN;
import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorMessage.ERROR_CODE_EXISTING_DOMAIN_NAME;
import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorMessage.ERROR_CODE_TENANT_DOES_NOT_MATCH_REGEX_PATTERN;

@Component
public class CentralService {

    private static final Log LOG = LogFactory.getLog(CentralService.class);
    private final JDBCConfig jdbcConfig;

    private final String tenantDomainRegex;
    private final Pattern tenantDomainPattern;

//    @Value("${tenant.domain.regex}")
//    public void setTenantDomainRegex(String tenantDomainRegex) {
//        this.tenantDomainRegex = tenantDomainRegex;
//        this.tenantDomainPattern = Pattern.compile(tenantDomainRegex);
//    }

    public CentralService(JDBCConfig jdbcConfig, String tenantDomainRegex) {

        this.jdbcConfig = jdbcConfig;
        this.tenantDomainRegex = tenantDomainRegex;
        this.tenantDomainPattern = Pattern.compile(tenantDomainRegex);
    }

    /**
     * Add a tenant to the system.
     *
     * @param tenantDomain Tenant domain.
     * @param userName     User name.
     */
    public void addTenant(String tenantDomain, String tenantUUID, String userName, String cluster)
            throws CentralServiceException {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Adding tenant.");
        }
        if (StringUtils.isEmpty(tenantDomain)) {
            throw handleClientErrors(ERROR_CODE_EMPTY_TENANT_DOMAIN);
        }
        if (!isMatchWithRegexPattern(tenantDomainPattern, tenantDomain)) {
            throw handleClientErrors(ERROR_CODE_TENANT_DOES_NOT_MATCH_REGEX_PATTERN, tenantDomain, tenantDomainRegex);
        }
        if (isDomainExists(tenantDomain)) {
            throw handleClientErrors(ERROR_CODE_EXISTING_DOMAIN_NAME, tenantDomain);
        }
        if (StringUtils.isEmpty(cluster)) {
            cluster = "default";
        }

        CentralServiceDAO centralServiceDAO = new CentralServiceDAO(jdbcConfig);
        centralServiceDAO.addTenant(tenantUUID, tenantDomain, userName, cluster);
    }

    /**
     * Delete a tenant from the system.
     *
     * @param tenantId  Tenant ID.
     */
    public void deleteTenant(String tenantId) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Deleting tenant.");
        }
        CentralServiceDAO centralServiceDAO = new CentralServiceDAO(jdbcConfig);
        centralServiceDAO.deleteTenant(tenantId);

    }

    /**
     * Check if a domain exists.
     *
     * @param tenantDomain Tenant domain.
     * @return True if the domain exists.
     */
    public boolean isDomainExists(String tenantDomain) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Checking if domain exists.");
        }

        CentralServiceDAO centralServiceDAO = new CentralServiceDAO(jdbcConfig);
        try {
            String tenantId = centralServiceDAO.getTenant(tenantDomain);
            if (StringUtils.isNotEmpty(tenantId)) {
                return true;
            }
        } catch (DatasourceException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private static boolean isMatchWithRegexPattern(Pattern pattern, String value) {

        return pattern.matcher(value).matches();
    }

    public static CentralServiceClientException handleClientErrors(ErrorMessage errorMessage, Object... params) {

        String errorMsg = errorMessage.getDescription();
        if (params != null) {
            errorMsg = String.format(errorMsg, params);
        }
        return new CentralServiceClientException(errorMsg, errorMessage.getCode());
    }
}
