/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.api.server.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConfigurationProperties(prefix = "central-service.cors")
public class CorsConfig {

    private static final Log LOG = LogFactory.getLog(CorsConfig.class);

    private String allowedOrigins;
    private String allowedMethods;
    private boolean allowCredentials;

    /**
     * Get allowed origins.
     *
     * @return Allowed origins.
     */
    public String getAllowedOrigins() {
        return allowedOrigins;
    }

    /**
     * Set allowed origins.
     *
     * @param allowedOrigins Allowed origins.
     */
    public void setAllowedOrigins(String allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    /**
     * Get allowed methods.
     *
     * @return Allowed methods.
     */
    public String getAllowedMethods() {
        return allowedMethods;
    }

    /**
     * Set allowed methods.
     *
     * @param allowedMethods Allowed methods.
     */
    public void setAllowedMethods(String allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    /**
     * Get whether credentials are allowed.
     *
     * @return Whether credentials are allowed.
     */
    public boolean isAllowCredentials() {
        return allowCredentials;
    }

    /**
     * Set whether credentials are allowed.
     *
     * @param allowCredentials Whether credentials are allowed.
     */
    public void setAllowCredentials(boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    /**
     * Register CORS configuration.
     *
     * @return {@link WebMvcConfigurer} object.
     */
    @Bean
    public WebMvcConfigurer webConfigurer() {

        LOG.info("Configuring CORS for allowed origins: " + allowedOrigins);
        LOG.info("Configuring CORS methods: " + allowedMethods);

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins.split(","))
                        .allowedMethods(allowedMethods.split(","))
                        .allowCredentials(allowCredentials);
            }
        };
    }
}
