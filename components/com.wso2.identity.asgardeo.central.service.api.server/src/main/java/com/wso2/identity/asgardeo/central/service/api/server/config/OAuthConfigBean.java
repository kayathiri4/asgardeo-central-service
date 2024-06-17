/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.api.server.config;

import com.wso2.identity.asgardeo.central.service.common.config.OAuthConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "central-service.oauth")
public class OAuthConfigBean implements OAuthConfig {

    private String introspectionEndpoint;
    private String clientId;
    private String clientSecret;
    private String clientSecretFilePath;
    private String trustStorePath;
    private String trustStorePassword;
    private String trustStorePasswordFilePath;
    private boolean ignoreCertificateValidation;

    private boolean disable;
    private List<String> allowedScopes;

    @Override
    public String getIntrospectionEndpoint() {
        return introspectionEndpoint;
    }

    @Override
    public void setIntrospectionEndpoint(String introspectionEndpoint) {
        this.introspectionEndpoint = introspectionEndpoint;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public String getClientSecretFilePath() {
        return clientSecretFilePath;
    }

    @Override
    public void setClientSecretFilePath(String clientSecretFilePath) {
        this.clientSecretFilePath = clientSecretFilePath;
    }

    @Override
    public boolean isIgnoreCertificateValidation() {
        return ignoreCertificateValidation;
    }

    @Override
    public void setIgnoreCertificateValidation(boolean ignoreCertificateValidation) {
        this.ignoreCertificateValidation = ignoreCertificateValidation;
    }

    @Override
    public String getTrustStorePath() {
        return trustStorePath;
    }

    @Override
    public void setTrustStorePath(String trustStorePath) {
        this.trustStorePath = trustStorePath;
    }

    @Override
    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    @Override
    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    @Override
    public String getTrustStorePasswordFilePath() {
        return trustStorePasswordFilePath;
    }

    @Override
    public void setTrustStorePasswordFilePath(String trustStorePasswordFilePath) {
        this.trustStorePasswordFilePath = trustStorePasswordFilePath;
    }

    /**
     * Get the allowed scopes.
     *
     * @return Allowed scopes.
     */
    public List<String> getAllowedScopes() {
        return allowedScopes;
    }

    /**
     * Set the allowed scopes.
     *
     * @param allowedScopes Allowed scopes.
     */
    public void setAllowedScopes(List<String> allowedScopes) {
        this.allowedScopes = allowedScopes;
    }

    /**
     * Get the disable status.
     *
     * @return Disable status.
     */
    public boolean isDisable() {
        return disable;
    }

    /**
     * Set the disable status.
     *
     * @param disable Disable status.
     */
    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
