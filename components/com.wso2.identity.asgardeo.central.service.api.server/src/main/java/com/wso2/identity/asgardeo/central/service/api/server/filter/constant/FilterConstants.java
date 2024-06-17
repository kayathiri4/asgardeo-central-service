/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.api.server.filter.constant;

import java.util.regex.Pattern;

public class FilterConstants {

    public static final String AUTHORIZATION_HEADER_NAME = "authorization";
    public static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    public static final String HEALTH_CHECK_URL = "/health";
    public static final String API_PATH_PATTERN = "/t/{tenantDomain}/api/asgardeo/feature-gate/{orgHandle}/**";

    public static final String HTTP_OPTIONS_METHOD = "OPTIONS";
    public static final String ACCESS_LOGGER = "ACCESS_LOG";

    public static final String USERNAME_ATTRIBUTE_KEY = "username";
    public static final String TENANT_DOMAIN_ATTRIBUTE_KEY = "tenantDomain";
    public static final String ORG_HANDLE_ATTRIBUTE_KEY = "orgHandle";
    public static final Pattern REGEX_FOR_SUB_ORG_ID = Pattern.compile("[a-z0-9]{8}(-[a-z0-9]{4}){3}-[a-z0-9]{12}");
}
