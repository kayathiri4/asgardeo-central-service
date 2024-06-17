/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.api.server.spring;

import com.wso2.identity.asgardeo.central.service.api.server.config.ServerConfig;
import com.wso2.identity.asgardeo.central.service.common.util.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.server.Ssl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * <p>
 * This class is used to initialize the server ssl key store from a password provided by a file. By default,
 * spring does not allow to get the server password from a file. Hence we need to override the mechanism and
 * use our logic to get the key store password from a file.
 * </p>
 *
 * @since 1.0.0
 * @version 1.0.0
 */
@Component
public class KeyStoreInit {

    private final Environment environment;
    private final ServerConfig serverConfig;

    /**
     * <p>
     *     Constructor of the class.
     * </p>
     * @param environment Environment
     * @param serverConfig ServerConfig
     */
    @Autowired
    public KeyStoreInit(Environment environment, ServerConfig serverConfig) {
        this.environment = environment;
        this.serverConfig = serverConfig;
    }

    /**
     * <p>
     *     Returns a custom server properties bean.
     * </p>
     * @return ServerProperties
     */
    @Bean
    @Primary
    public ServerProperties serverProperties() {

        final ServerProperties serverProperties = new ServerProperties();
        Ssl ssl = new Ssl();
        String keystorePassword = PasswordManager.getPasswordFromFile(serverConfig.getKeyStorePasswordFile());
        ssl.setKeyPassword(keystorePassword);
        System.setProperty("server.ssl.key-store-password", keystorePassword);
        serverProperties.setSsl(ssl);
        return serverProperties;
    }
}
