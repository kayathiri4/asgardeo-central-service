/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.common.config;

/**
 * OAuth's configuration interface.
 * This interface is used to keep the configuration needs to create a successful OAuth connection.
 */
public interface OAuthConfig {

    /**
     * Get the introspection endpoint.
     *
     * @return Introspection endpoint.
     */
    String getIntrospectionEndpoint();

    /**
     * Set the introspection endpoint.
     *
     * @param introspectionEndpoint Introspection endpoint.
     */
    void setIntrospectionEndpoint(String introspectionEndpoint);

    /**
     * Get the client ID.
     *
     * @return Client ID.
     */
    String getClientId();

    /**
     * Set the client ID.
     *
     * @param clientId Client ID.
     */
    void setClientId(String clientId);

    /**
     * Get the client secret.
     *
     * @return Client secret.
     */
    String getClientSecret();

    /**
     * Set the client secret.
     *
     * @param clientSecret Client secret.
     */
    void setClientSecret(String clientSecret);

    /**
     * Get the client secret file path.
     *
     * @return Client secret file path.
     */
    String getClientSecretFilePath();

    /**
     * Set the client secret file path.
     *
     * @param clientSecretFilePath Client secret file path.
     */
    void setClientSecretFilePath(String clientSecretFilePath);

    /**
     * Ignore certificate validation.
     *
     * @return True if ignore certificate validation.
     */
    boolean isIgnoreCertificateValidation();

    /**
     * Set ignore certificate validation.
     *
     * @param ignoreCertificateValidation True if ignore certificate validation.
     */
    void setIgnoreCertificateValidation(boolean ignoreCertificateValidation);

    /**
     * Get trust store path.
     * @return Trust store path.
     */
    String getTrustStorePath();

    /**
     * Set trust store path.
     * @param trustStorePath Trust store path.
     */
    void setTrustStorePath(String trustStorePath);

    /**
     * Get trust store password.
     * @return Trust store password.
     */
    String getTrustStorePassword();

    /**
     * Set trust store password.
     * @param trustStorePassword Trust store password.
     */
    void setTrustStorePassword(String trustStorePassword);

    /**
     * Get the allowed scopes.
     *
     * @return Allowed scopes.
     */
    String getTrustStorePasswordFilePath();

    /**
     * Set the allowed scopes.
     *
     * @param trustStorePasswordFilePath Allowed scopes.
     */
    void setTrustStorePasswordFilePath(String trustStorePasswordFilePath);
}
