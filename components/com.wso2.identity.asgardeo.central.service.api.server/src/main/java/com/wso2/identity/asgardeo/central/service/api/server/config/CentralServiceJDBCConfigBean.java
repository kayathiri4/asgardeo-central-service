/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.api.server.config;

import com.wso2.identity.asgardeo.central.service.core.config.CentralServiceJDBCConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "central-service.jdbc")
public class CentralServiceJDBCConfigBean extends CentralServiceJDBCConfig {

    /**
     * <p>
     * Constructor of the class.
     * </p>
     */
    public CentralServiceJDBCConfigBean() {

        super();
    }


}
