/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.common.exception;

public class DatasourceException extends CentralServiceException {

    public DatasourceException(String message, String errorCode) {

        super(message, errorCode);
    }

    public DatasourceException(String message, String errorCode, Throwable cause) {

        super(message, errorCode, cause);
    }
}