/*
 * Copyright (c) 2023, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 *
 */

package com.wso2.identity.asgardeo.central.service.common.exception;
/**
 * Key store handling exception.
 * This exception is used to handle the exceptions related to key store handling.
 */
public class KeyStoreHandlingException extends CentralServiceException {

    /**
     * Constructor with the message.
     *
     * @param message   The message to be thrown.
     * @param errorCode The error code.
     */
    public KeyStoreHandlingException(String message, String errorCode) {
        super(message, errorCode);
    }

    /**
     * Constructor with the message and the cause.
     *
     * @param message   The message to be thrown.
     * @param errorCode The error code.
     * @param cause     The cause of the exception.
     */
    public KeyStoreHandlingException(String message, String errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }
}
