package com.wso2.identity.asgardeo.central.service.rest.api.v1.impl;

import com.wso2.identity.asgardeo.central.service.common.config.JDBCConfig;
import com.wso2.identity.asgardeo.central.service.common.exception.CentralServiceException;
import com.wso2.identity.asgardeo.central.service.common.exception.DatasourceException;
import com.wso2.identity.asgardeo.central.service.core.CentralService;
import com.wso2.identity.asgardeo.central.service.rest.api.v1.TenantsApiDelegate;
import com.wso2.identity.asgardeo.central.service.rest.api.v1.model.TenantReserveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Implementation of the TenantsAPI.
 */
@Component
public class TenantsApiImpl implements TenantsApiDelegate {

    private CentralService centralService;

    /**
     * Constructor of the class.
     *
     * @param jdbcConfig JDBCConfig.
     */
    @Autowired
    public TenantsApiImpl(JDBCConfig jdbcConfig, @Value("${tenant.domain.regex}") String tenantDomainRegex) {

        this.centralService = new CentralService(jdbcConfig, tenantDomainRegex);
    }

    /**
     * Getter for the CentralService.
     *
     * @return CentralService.
     */
    public CentralService getCentralService() {

        return centralService;
    }

    /**
     * Setter for the CentralService.
     *
     * @param centralService CentralService.
     */
    public void setCentralService(CentralService centralService) {

        this.centralService = centralService;
    }

    @Override
    public ResponseEntity<Void> addTenantForRegistration(TenantReserveRequest tenantReserveRequest) {

        try {
            centralService.addTenant(tenantReserveRequest.getDomain(), tenantReserveRequest.getDomainId(),
                    tenantReserveRequest.getUsername(), tenantReserveRequest.getCluster());
            return ResponseEntity.ok().build();
        } catch (DatasourceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (CentralServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Void> deleteTenant(String tenantId) {

        centralService.deleteTenant(tenantId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> isDomainExist(String domain, String username) {

        boolean isDomainExist = centralService.isDomainExists(domain);
        if (isDomainExist) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
