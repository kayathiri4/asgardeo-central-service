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
 * OAuth's exception.
 * This exception is used to handle the exceptions related to OAuth.
 */
public class OAuthException extends CentralServiceException {

    /**
     * <p>Constructor with the message.</p>
     *
     * @param message   The message to be thrown.
     * @param errorCode The error code.
     */
    public OAuthException(String message, String errorCode) {
        super(message, errorCode);
    }

    /**
     * <p>Constructor with the message and the cause.</p>
     *
     * @param message   The message to be thrown.
     * @param errorCode The error code.
     * @param cause     The cause of the exception.
     */
    public OAuthException(String message, String errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }

    /**
     * <p>Constructor with the cause.</p>
     *
     * @param cause     The cause of the exception.
     * @param errorCode The error code.
     */
    public OAuthException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }
}
