/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.api.server;

import com.wso2.identity.asgardeo.central.service.api.server.config.ServerConfig;
import com.wso2.identity.asgardeo.central.service.api.server.filter.model.AccessLogMetadata;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.StringJoiner;
import java.util.UUID;

import static com.wso2.identity.asgardeo.central.service.api.server.filter.constant.FilterConstants.ACCESS_LOGGER;
import static com.wso2.identity.asgardeo.central.service.common.constant.CommonConstants.CORRELATION_ID_KEY;
import static com.wso2.identity.asgardeo.central.service.common.constant.CommonConstants.CO_RELATION_HEADER;

/**
 * <b>Access Log filter for Feature Gate.</b>
 * <p>This filter will be used to log the access information to the feature gate and keep the co-relation id from
 * different services. This will be the <b>"First"</b> filter to be executed.
 * </p>
 *
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@Order(1)
public class AccessLogFilter extends GenericFilterBean {

    private static final Log LOG = LogFactory.getLog(AccessLogFilter.class);
    private static final Log ACCESS_LOG = LogFactory.getLog(ACCESS_LOGGER);

    private final ServerConfig serverConfig;

    /**
     * Default constructor for the access log filer.
     * @param serverConfig Server config {@link ServerConfig}
     */
    @Autowired
    public AccessLogFilter(ServerConfig serverConfig) {

        this.serverConfig = serverConfig;
        if (serverConfig.isDisableAccessLogs()) {
            LOG.warn("Access Log filter is disabled from the configuration for Asgardeo Feature Gate. " +
                    "No access logs will be printed.");
            return;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Access Log filter successfully engaged.");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        if (serverConfig.isDisableAccessLogs()) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String coRelationId = httpServletRequest.getHeader(CO_RELATION_HEADER);
        AccessLogMetadata accessLogMetadata = null;

        // Check whether the correlation id is available in the request. If not, generate a new one.
        if (StringUtils.isBlank(coRelationId)) {
            coRelationId = UUID.randomUUID().toString();
            if (LOG.isDebugEnabled()) {
                LOG.debug(CORRELATION_ID_KEY + " not available in request. Setting correlation id to: " + coRelationId);
            }
        }

        try {
            // Set the correlation id to the thread context.
            ThreadContext.put(CORRELATION_ID_KEY, coRelationId);

            // Generate the access log metadata now. We need to do this before the filter chain is executed because
            // we calculate the duration of the request in the metadata.
            accessLogMetadata = new AccessLogMetadata(httpServletRequest, httpServletResponse);

            // Execute the filter chain.
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Throwable ex) {
            LOG.error("Error occurred while executing the access log filter.", ex);
            httpServletResponse.reset();
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        } finally {
            // Log the access data.
            try {
                logAccessData(accessLogMetadata, httpServletRequest, httpServletResponse);
            } catch (Throwable ex) {
                LOG.error("Error occurred while writing the access log.", ex);
                httpServletResponse.reset();
                httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
            ThreadContext.remove(CORRELATION_ID_KEY);
        }
    }

    private void logAccessData(AccessLogMetadata accessLogMetadata, HttpServletRequest httpServletRequest,
                              HttpServletResponse httpServletResponse) {

        // In an error scenario, this can be null. So, we can regenerate the metadata and try to log them. Only
        // execution time will be loosing here.
        if (accessLogMetadata == null) {
            accessLogMetadata = new AccessLogMetadata(httpServletRequest, httpServletResponse);
        }

        // Log the access log finally with the metadata and the execution time.
        StringJoiner logString = new StringJoiner(" ");
        logString.add(accessLogMetadata.getRemoteIP())
                .add("[" + accessLogMetadata.getReceivedDateTime() + "]")
                .add(accessLogMetadata.getMethodType())
                .add(accessLogMetadata.getRequestURI())
                .add(accessLogMetadata.getResponseCode())
                .add(accessLogMetadata.getUserAgent())
                .add(" " + accessLogMetadata.getDurationInMillis());
        ACCESS_LOG.info(logString.toString());
    }
}
