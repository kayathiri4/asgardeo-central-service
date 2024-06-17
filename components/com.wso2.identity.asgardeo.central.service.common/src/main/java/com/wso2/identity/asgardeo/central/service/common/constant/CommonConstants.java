/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.common.constant;

public class CommonConstants {

    public static final String CO_RELATION_HEADER = "X-Azure-Ref";
    public static final String CORRELATION_ID_KEY = "Correlation-ID";

    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;

    public static final String AUTH_HEADER_PREFIX = "Basic ";
    public static final String ACTIVE = "active";
    public static final String SCOPE = "scope";
    public static final String USERNAME_ATTRIBUTE_KEY = "username";
    public static final String ORG_ID = "org_id";

}
