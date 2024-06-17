/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.api.server.filter.model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static com.wso2.identity.asgardeo.central.service.common.constant.CommonConstants.CORRELATION_ID_KEY;

/**
 * Holds the access log metadata.
 */
public class AccessLogMetadata {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss Z";
    private static final String USER_AGENT_KEY = "user-agent";

    private String correlationId;
    private Instant receivedTime;
    private long callStart;
    private String remoteIP;
    private String userAgent;
    private String methodType;
    private String responseCode;
    private String requestURI;

    /**
     * Constructor with http request and http response.
     *
     * @param httpRequest Http request which used to get metadata from the request.
     * @param httpResponse Http response which used to get http status from the response.
     */
    public AccessLogMetadata(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        this.correlationId =  ThreadContext.get(CORRELATION_ID_KEY);
        this.receivedTime = Instant.now();
        this.callStart = System.nanoTime();
        this.remoteIP = httpRequest.getRemoteAddr();
        this.methodType = httpRequest.getMethod();
        this.userAgent = httpRequest.getHeader(USER_AGENT_KEY);
        this.responseCode = String.valueOf(httpResponse.getStatus());
        this.requestURI = httpRequest.getRequestURI();
    }

    /**
     * Get the correlation id.
     *
     * @return Correlation id.
     */
    public String getCorrelationId() {

        return correlationId;
    }

    /**
     * Set the correlation id.
     *
     * @param correlationId Correlation id.
     */
    public void setCorrelationId(String correlationId) {

        this.correlationId = correlationId;
    }

    /**
     * Get the received time.
     *
     * @return Received time.
     */
    public Instant getReceivedTime() {

        return receivedTime;
    }

    /**
     * Set the received time.
     *
     * @param receivedTime Received time.
     */
    public void setReceivedTime(Instant receivedTime) {

        this.receivedTime = receivedTime;
    }

    /**
     * Get the call start time.
     *
     * @return Call start time.
     */
    public long getCallStart() {

        return callStart;
    }

    /**
     * Set the call start time.
     *
     * @param callStart Call start time.
     */
    public void setCallStart(long callStart) {

        this.callStart = callStart;
    }

    /**
     * Get the remote ip.
     *
     * @return Remote ip.
     */
    public String getRemoteIP() {

        return remoteIP;
    }

    /**
     * Set the remote ip.
     *
     * @param remoteIP Remote ip.
     */
    public void setRemoteIP(String remoteIP) {

        this.remoteIP = remoteIP;
    }

    /**
     * Get the user agent.
     *
     * @return User agent.
     */
    public String getUserAgent() {

        return userAgent;
    }

    /**
     * Set the user agent.
     *
     * @param userAgent User agent.
     */
    public void setUserAgent(String userAgent) {

        this.userAgent = userAgent;
    }

    /**
     * Get the method type.
     *
     * @return Method type.
     */
    public String getMethodType() {

        return methodType;
    }

    /**
     * Set the method type.
     *
     * @param methodType Method type.
     */
    public void setMethodType(String methodType) {

        this.methodType = methodType;
    }

    /**
     * Get the response code.
     *
     * @return Response code.
     */
    public String getResponseCode() {

        return responseCode;
    }

    /**
     * Set the response code.
     *
     * @param responseCode Response code.
     */
    public void setResponseCode(String responseCode) {

        this.responseCode = responseCode;
    }

    /**
     * Get the request uri.
     *
     * @return Request uri.
     */
    public String getRequestURI() {

        return requestURI;
    }

    /**
     * Set the request uri.
     *
     * @param requestURI Request uri.
     */
    public void setRequestURI(String requestURI) {

        this.requestURI = requestURI;
    }

    /**
     * Calculate the time took to serve the request.
     *
     * @return Elapsed time in milliseconds.
     */
    public long getDurationInMillis() {

        long callEnd = System.nanoTime();
        return TimeUnit.NANOSECONDS.toMillis(callEnd - callStart);
    }

    /**
     * Format the unix time in readable UTC zone format.
     *
     * @return UTC zone format timestamp.
     */
    public String getReceivedDateTime() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
                .withZone(ZoneId.from(ZoneOffset.UTC));
        return formatter.format(receivedTime);
    }


    @Override
    public String toString() {

        return "AccessLogMetadata{" +
                "correlationId='" + correlationId + '\'' +
                ", receivedTime=" + receivedTime +
                ", callStart=" + callStart +
                ", remoteIP='" + remoteIP + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", methodType='" + methodType + '\'' +
                '}';
    }
}
