/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.api.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("central-service.server")
public class ServerConfig {

    private String keyStorePasswordFile;
    private boolean disableAccessLogs;

    /**
     * <p> Get the key store password file path. </p>
     * @return The key store password file path.
     */
    public String getKeyStorePasswordFile() {

        return keyStorePasswordFile;
    }

    /**
     * <p> Set the key store password file path. </p>
     * @param keyStorePasswordFile The key store password file path.
     */
    public void setKeyStorePasswordFile(String keyStorePasswordFile) {

        this.keyStorePasswordFile = keyStorePasswordFile;
    }

    /**
     * <p> Check whether the access logs are disabled. </p>
     * @return True if the access logs are disabled.
     */
    public boolean isDisableAccessLogs() {

        return disableAccessLogs;
    }

    /**
     * <p> Set whether the access logs are disabled. </p>
     * @param disableAccessLogs True if the access logs are disabled.
     */
    public void setDisableAccessLogs(boolean disableAccessLogs) {

        this.disableAccessLogs = disableAccessLogs;
    }
}
