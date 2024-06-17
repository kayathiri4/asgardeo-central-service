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
 * Base runtime exception class.
 * This class is used to represent the base runtime exceptions for feature gate.
 */
public class CentralServiceRuntimeException extends RuntimeException implements ErrorCoded {

    private String errorCode;

    /**
     * <p>
     * Constructor with the message.
     * </p>
     * @param message The message to be thrown.
     * @param errorCode The error code.
     */
    public CentralServiceRuntimeException(String message, String errorCode) {

        super(message);
        this.errorCode = errorCode;
    }

    /**
     * <p>
     * Constructor with the message and the cause.
     * </p>
     * @param message The message to be thrown.
     * @param cause The cause of the exception.
     */
    public CentralServiceRuntimeException(String message, String errorCode, Throwable cause) {

        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * <p>Error code of the exception.</p>
     * @return The error code.
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * <p>Sets the error code of the exception.</p>
     * @param errorCode The error code.
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
