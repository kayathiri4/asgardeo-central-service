/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.common.exception;

/**
 * Marker interface for error coded exceptions.
 * This interface is used to mark the exception classes with error codes.
 */
public interface ErrorCoded {

    /**
     * <p>
     * Returns the error code.
     * </p>
     *
     * @return The error code.
     */
    String getErrorCode();

    /**
     * <p>
     * Sets the error code.
     * </p>
     *
     * @param errorCode The error code.
     */
    void setErrorCode(String errorCode);
}
