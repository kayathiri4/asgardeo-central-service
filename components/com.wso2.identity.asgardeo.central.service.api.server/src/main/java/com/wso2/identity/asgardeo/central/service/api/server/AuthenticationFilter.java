/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.api.server;

import com.wso2.identity.asgardeo.central.service.api.server.config.OAuthConfigBean;
import com.wso2.identity.asgardeo.central.service.common.exception.ErrorCoded;
import com.wso2.identity.asgardeo.central.service.common.oauth.IntrospectionHandler;
import com.wso2.identity.asgardeo.central.service.common.oauth.model.IntrospectionResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static com.wso2.identity.asgardeo.central.service.api.server.filter.constant.FilterConstants.API_PATH_PATTERN;
import static com.wso2.identity.asgardeo.central.service.api.server.filter.constant.FilterConstants.AUTHORIZATION_HEADER_NAME;
import static com.wso2.identity.asgardeo.central.service.api.server.filter.constant.FilterConstants.AUTHORIZATION_HEADER_PREFIX;
import static com.wso2.identity.asgardeo.central.service.api.server.filter.constant.FilterConstants.HEALTH_CHECK_URL;
import static com.wso2.identity.asgardeo.central.service.api.server.filter.constant.FilterConstants.HTTP_OPTIONS_METHOD;
import static com.wso2.identity.asgardeo.central.service.api.server.filter.constant.FilterConstants.ORG_HANDLE_ATTRIBUTE_KEY;
import static com.wso2.identity.asgardeo.central.service.api.server.filter.constant.FilterConstants.REGEX_FOR_SUB_ORG_ID;
import static com.wso2.identity.asgardeo.central.service.api.server.filter.constant.FilterConstants.TENANT_DOMAIN_ATTRIBUTE_KEY;
import static com.wso2.identity.asgardeo.central.service.api.server.filter.constant.FilterConstants.USERNAME_ATTRIBUTE_KEY;

/**
 * <b>Authentication Filter for Asgardeo Feature Gate.</b>
 * <p>This class is responsible for authenticating the incoming API requests.</p>
 *
 * @since 1.0.0
 * @version 1.0.0
 */
@Component
@Order(2)
public class AuthenticationFilter extends GenericFilterBean {

    private static final Log LOG = LogFactory.getLog(AuthenticationFilter.class);

    private final OAuthConfigBean oAuthConfigBean;

    /**
     * Constructor of the AuthenticationFilter class.
     * @param oAuthConfigBean OAuth2 configuration bean. {@link OAuthConfigBean}
     */
    @Autowired
    public AuthenticationFilter(OAuthConfigBean oAuthConfigBean) {

        this.oAuthConfigBean = oAuthConfigBean;
        if (oAuthConfigBean.isDisable()) {
            LOG.warn("Authentication is disabled for Asgardeo Feature Gate. APIs are are exposed without " +
                    "authentication.");
            return;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Authentication filter successfully engaged.");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        if (isMtlsSecured(request)) {
            //return;
        }

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String tenantDomain = "undefined";

        request.setAttribute(USERNAME_ATTRIBUTE_KEY, "dummy");
        request.setAttribute(TENANT_DOMAIN_ATTRIBUTE_KEY, "dummy");
        filterChain.doFilter(request, response);

//        try {
//            if (oAuthConfigBean.isDisable()) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            // Allow health check requests to pass through without authentication.
//            if (servletRequest.getRequestURI().contains(HEALTH_CHECK_URL)) {
//                if (LOG.isDebugEnabled()) {
//                    LOG.debug("Allowing health check request to pass through without authentication.");
//                }
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            // Allow HTTP OPTIONS requests to pass through without authentication.
//            if (servletRequest.getMethod().equals(HTTP_OPTIONS_METHOD)) {
//                if (LOG.isDebugEnabled()) {
//                    LOG.debug("Allowing HTTP OPTIONS request to pass through without authentication.");
//                }
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            String authorizationHeader = servletRequest.getHeader(AUTHORIZATION_HEADER_NAME);
//            if (authorizationHeader == null) {
//                httpServletResponse.reset();
//                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
//                return;
//            }
//
//            // We need to change the introspection endpoint based on the tenant domain.
//            tenantDomain = getTenantDomainFromRequest(servletRequest);
//            String introspectionEndpoint = oAuthConfigBean.getIntrospectionEndpoint();
//            String tenantedIntrospectionEndpoint = introspectionEndpoint.replace("{tenant-domain}", tenantDomain);
//
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("Introspection endpoint for tenant: " + tenantDomain + " is set to: " +
//                        tenantedIntrospectionEndpoint + ".");
//            }
//
//            IntrospectionHandler introspectionHandler = new IntrospectionHandler(tenantedIntrospectionEndpoint,
//                    oAuthConfigBean);
//            String accessToken = authorizationHeader.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
//            IntrospectionResponse introspectionResponse = introspectionHandler.introspect(accessToken);
//
//            // Validate the token.
//            if (!isTokenValid(introspectionResponse, tenantDomain)) {
//                httpServletResponse.reset();
//                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
//                return;
//            }
//
//            // Find whether the org handle belongs to calling tenant.
//            String orgHandle = getOrgHandleFromRequest(servletRequest);
//            boolean isOrgHandleEqualsTenant = isTokenIssuedForCallingTenant(introspectionResponse, orgHandle);
//
//            if (isOrgHandleEqualsTenant) {
//                if (LOG.isDebugEnabled()) {
//                    LOG.debug("Successfully authenticated the request from the organization: " + tenantDomain);
//                }
//                request.setAttribute(USERNAME_ATTRIBUTE_KEY, introspectionResponse.getUsername());
//                request.setAttribute(TENANT_DOMAIN_ATTRIBUTE_KEY, introspectionResponse.getTenantDomain());
//                filterChain.doFilter(request, response);
//            } else {
//                if (LOG.isDebugEnabled()) {
//                    LOG.debug("Authentication failed for the request from the organization: " + tenantDomain);
//                }
//                httpServletResponse.reset();
//                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
//            }
//        } catch (Throwable e) {
//            String errorCode = "NO-CODE";
//            if (e instanceof ErrorCoded) {
//                errorCode = ((ErrorCoded) e).getErrorCode();
//            }
//            LOG.error(errorCode + " : " + "Error occurred when validation access token for tenant: " +
//            tenantDomain, e);
//            httpServletResponse.reset();
//            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        }
    }

    private String getTenantDomainFromRequest(HttpServletRequest servletRequest) {

        AntPathMatcher matcher = new AntPathMatcher();
        String requestURI = servletRequest.getRequestURI();
        return matcher.extractUriTemplateVariables(API_PATH_PATTERN, requestURI).get(TENANT_DOMAIN_ATTRIBUTE_KEY);
    }

    private String getOrgHandleFromRequest(HttpServletRequest servletRequest) {

        AntPathMatcher matcher = new AntPathMatcher();
        String requestURI = servletRequest.getRequestURI();
        return matcher.extractUriTemplateVariables(API_PATH_PATTERN, requestURI).get(ORG_HANDLE_ATTRIBUTE_KEY);
    }

    private boolean isTokenValid(IntrospectionResponse introspectionResponse, String tenantDomain) {

        // Token is not active.
        if (!introspectionResponse.isActive()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(String.format("Token for tenant: %s is not active", tenantDomain));
            }
            return false;
        }

        // User does not have the required scopes.
        if ((oAuthConfigBean.getAllowedScopes() != null && !oAuthConfigBean.getAllowedScopes().isEmpty())
                && (introspectionResponse.getScopes() == null || !introspectionResponse.getScopes()
                .containsAll(oAuthConfigBean.getAllowedScopes()))) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(String.format("Token for tenant:%s does not have the required scopes", tenantDomain));
            }
            return false;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Authorization successful for tenant: " + tenantDomain + ".");
        }
        return true;
    }

    private boolean isTokenIssuedForCallingTenant(IntrospectionResponse introspectionResponse, String orgHandle) {

        // Check if the org handle matches the pattern of a sub organization UUID.
        if (REGEX_FOR_SUB_ORG_ID.matcher(orgHandle).matches()) {
            return orgHandle.equals(introspectionResponse.getOrgId());
        }
        return orgHandle.equals(introspectionResponse.getTenantDomain());
    }

    /**
     * Check whether the requested API is MTLS secured.
     * Website Tenant creation and validation requests are comes to these APIs.
     *
     * @param request ServletRequest
     * @return Whether the request is a MTLS secured or not.
     */
    private boolean isMtlsSecured(ServletRequest request) {

        // All the APIs are secured by MTLS for now.
        return true;
    }
}
