/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.common.constant;

public enum ErrorMessage {

    ERROR_CODE_EMPTY_TENANT_DOMAIN("CS_10001", "Empty tenant domain", "Tenant domain cannot be empty"),
    ERROR_CODE_TENANT_DOES_NOT_MATCH_REGEX_PATTERN("CS-10002", "Invalid tenant domain",
            "Invalid tenant domain: %s. Domain should match the regex pattern %s."),
    ERROR_CODE_EXISTING_DOMAIN_NAME("CS-10003", "Tenant domain name already exists",
            "Tenant domain name: %s already exists in the system");

    private final String code;
    private final String message;
    private final String description;

    ErrorMessage(String code, String message, String description) {

        this.code = code;
        this.message = message;
        this.description = description;
    }

    public String getCode() {

        return code;
    }

    public String getMessage() {

        return message;
    }

    public String getDescription() {

        return this.description;
    }

    @Override
    public String toString() {

        return code + " : " + message;
    }
}
