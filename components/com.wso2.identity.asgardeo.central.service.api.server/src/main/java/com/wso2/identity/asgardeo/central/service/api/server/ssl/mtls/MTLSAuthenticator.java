package com.wso2.identity.asgardeo.central.service.api.server.ssl.mtls;

import jakarta.servlet.ServletRequest;

/**
 * Service interface for certificate based two-way authentication.
 */
public interface MTLSAuthenticator {

    /**
     * Validate the request by checking whether the request contains X509Certificate and using thumb print validation.
     *
     * @param request Servlet Request.
     * @return whether request is authenticated or not.
     */
    public boolean isAuthenticated(ServletRequest request);
}

