/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.common.oauth;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.TokenIntrospectionRequest;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import com.nimbusds.oauth2.sdk.token.Token;
import com.nimbusds.oauth2.sdk.util.tls.TLSUtils;
import com.nimbusds.oauth2.sdk.util.tls.TLSVersion;
import com.wso2.identity.asgardeo.central.service.common.config.OAuthConfig;
import com.wso2.identity.asgardeo.central.service.common.exception.KeyStoreHandlingException;
import com.wso2.identity.asgardeo.central.service.common.exception.OAuthException;
import com.wso2.identity.asgardeo.central.service.common.oauth.model.IntrospectionResponse;
import com.wso2.identity.asgardeo.central.service.common.util.KeyStoreManager;
import com.wso2.identity.asgardeo.central.service.common.util.PasswordManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.ThreadContext;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.wso2.identity.asgardeo.central.service.common.constant.CommonConstants.ACTIVE;
import static com.wso2.identity.asgardeo.central.service.common.constant.CommonConstants.AUTH_HEADER_PREFIX;
import static com.wso2.identity.asgardeo.central.service.common.constant.CommonConstants.CORRELATION_ID_KEY;
import static com.wso2.identity.asgardeo.central.service.common.constant.CommonConstants.CO_RELATION_HEADER;
import static com.wso2.identity.asgardeo.central.service.common.constant.CommonConstants.FORBIDDEN;
import static com.wso2.identity.asgardeo.central.service.common.constant.CommonConstants.NOT_FOUND;
import static com.wso2.identity.asgardeo.central.service.common.constant.CommonConstants.ORG_ID;
import static com.wso2.identity.asgardeo.central.service.common.constant.CommonConstants.SCOPE;
import static com.wso2.identity.asgardeo.central.service.common.constant.CommonConstants.UNAUTHORIZED;
import static com.wso2.identity.asgardeo.central.service.common.constant.CommonConstants.USERNAME_ATTRIBUTE_KEY;
import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorCodes.ERROR_CODE_CEN_SC_10011;
import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorCodes.ERROR_CODE_CEN_SC_10012;
import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorCodes.ERROR_CODE_CEN_SC_10013;
import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorCodes.ERROR_CODE_CEN_SC_10014;
import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorCodes.ERROR_CODE_CEN_SC_10015;
import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorCodes.ERROR_CODE_CEN_SC_10016;
import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorCodes.ERROR_CODE_CEN_SC_10017;

/**
 * OAuth 2.0 Introspection Handler.
 * This class is used to introspect the give access token aginst the give OAuth provider.
 */
public class IntrospectionHandler {

    private static final Log LOG = LogFactory.getLog(IntrospectionHandler.class);

    private final OAuthConfig oAuthConfig;
    private final String introspectionEndpoint;

    private static final String APP_TENANT_QUERY_PARAM = "appTenant";
    private static final String APPLICATION_TENANT_DOMAIN = "carbon.super";

    /**
     * Constructor of IntrospectionHandler.
     *
     * @param oAuthConfig           OAuth 2.0 configurations related introspection.
     * @param introspectionEndpoint Introspection endpoint of the OAuth provider.
     */
    public IntrospectionHandler(String introspectionEndpoint, OAuthConfig oAuthConfig) {

        this.oAuthConfig = oAuthConfig;
        this.introspectionEndpoint = introspectionEndpoint;
    }

    /**
     * Introspect the given access token against the OAuth provider.
     *
     * @param token Access token to be introspected.
     * @return Introspection response.
     * @throws OAuthException If an error occurs while introspecting the token.
     */
    public IntrospectionResponse introspect(String token) throws OAuthException {

        if (oAuthConfig == null || StringUtils.isBlank(introspectionEndpoint)
                || StringUtils.isBlank(oAuthConfig.getTrustStorePath()) || StringUtils.isBlank(token)) {
            throw new OAuthException("OAuth 2.0 configuration is not provided.", ERROR_CODE_CEN_SC_10017);
        }

        try {
            URI endpoint = URI.create(introspectionEndpoint);
            String clientSecret = oAuthConfig.getClientSecret();
            if (StringUtils.isBlank(clientSecret)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Client secret is not provided. Trying to read it from the file.");
                }
                // If no direct client secret is provided, read it from the file.
                clientSecret = PasswordManager.getPasswordFromFile(oAuthConfig.getClientSecretFilePath());
            }

            // Base64 encode the client id and client secret (RFC 6749 Section 2.3.1
            String authHeader = oAuthConfig.getClientId() + ":" + clientSecret;
            byte[] authHeaderInBytes = authHeader.getBytes(StandardCharsets.UTF_8);

            Token accessToken = new BearerAccessToken(token);
            Map<String, List<String>> params = new HashMap<>();
            params.put(APP_TENANT_QUERY_PARAM, Collections.singletonList(APPLICATION_TENANT_DOMAIN));
            TokenIntrospectionRequest request = new TokenIntrospectionRequest(endpoint, accessToken, params);
            HTTPRequest httpRequest = request.toHTTPRequest();

            String trustStorePassword = PasswordManager.getPasswordFromFile(oAuthConfig
                    .getTrustStorePasswordFilePath());
            KeyStore trustStore = KeyStoreManager.getKeystore(oAuthConfig.getTrustStorePath(),
                    trustStorePassword.toCharArray(), KeyStore.getDefaultType());

            SSLSocketFactory sslSocketFactory = buildSSLSocketFactory(trustStore,
                    oAuthConfig.isIgnoreCertificateValidation());
            httpRequest.setSSLSocketFactory(sslSocketFactory);
            if (oAuthConfig.isIgnoreCertificateValidation()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Hostname verification is disabled.");
                }
                httpRequest.setHostnameVerifier((hostName, sslSession) -> true);
            }

            // Get co-relation id to send with the request.
            String coRelationId = ThreadContext.get(CORRELATION_ID_KEY);
            if (StringUtils.isBlank(coRelationId)) {
                // This is an unlikely scenario. Can happen if access logger filter is disabled.
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Co-relation id is not set in the thread context. Generating a new one.");
                }
                coRelationId = UUID.randomUUID().toString();
            }

            httpRequest.setHeader(CO_RELATION_HEADER, coRelationId);
            httpRequest.setAuthorization(AUTH_HEADER_PREFIX + new String(Base64.getEncoder().encode(authHeaderInBytes),
                    StandardCharsets.UTF_8));

            HTTPResponse httpResponse = httpRequest.send();
            return buildIntrospectionResponse(httpResponse);
        } catch (KeyStoreHandlingException | IOException ex) {
            throw new OAuthException("Error occurred when validating access token.", ERROR_CODE_CEN_SC_10011, ex);
        }
    }

    private IntrospectionResponse buildIntrospectionResponse(HTTPResponse httpResponse) throws OAuthException {

        if (httpResponse == null) {
            throw new OAuthException("Error occurred when validating access token.", ERROR_CODE_CEN_SC_10012);
        }

        if (httpResponse.getStatusCode() == UNAUTHORIZED || httpResponse.getStatusCode() == FORBIDDEN) {
            throw new OAuthException("Error authenticating with the introspect endpoint", ERROR_CODE_CEN_SC_10013);
        }

        if (httpResponse.getStatusCode() == NOT_FOUND) {
            throw new OAuthException("Error occurred due to invoking introspect endpoint of a non-existing " +
                    "tenant domain.", ERROR_CODE_CEN_SC_10014);
        }

        try {
            IntrospectionResponse introspectionResponse = new IntrospectionResponse();
            introspectionResponse.setActive(Boolean.parseBoolean(httpResponse.getContentAsJSONObject()
                    .get(ACTIVE).toString()));

            if (!introspectionResponse.isActive()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("The provided token is not 'active'");
                }
                // If active state is false, then username information will not be in the response.
                return introspectionResponse;
            }

            Set<String> scopes = httpResponse.getContentAsJSONObject().get(SCOPE) != null
                    ? new HashSet<>(Arrays.asList(httpResponse.getContentAsJSONObject().get(SCOPE)
                    .toString().split("\\s+")))
                    : null;

            String username = httpResponse.getContentAsJSONObject().get(USERNAME_ATTRIBUTE_KEY).toString();
            if (StringUtils.isBlank(username)) {
                throw new OAuthException("Error occurred when validating access token", ERROR_CODE_CEN_SC_10015);
            }

            // Extract the username without the tenant domain.
            introspectionResponse.setUsername(username.substring(0, username.lastIndexOf("@")));
            // Extract the tenant domain.
            introspectionResponse.setTenantDomain(username.substring(username.lastIndexOf("@") + 1));
            // Extract the issued organization.
            if (httpResponse.getContentAsJSONObject().get(ORG_ID) != null) {
                introspectionResponse.setOrgId(
                        httpResponse.getContentAsJSONObject().get(ORG_ID).toString());
            }
            // Extract the scopes.
            introspectionResponse.setScopes(scopes);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Token introspection completed successfully for organization: "
                        + introspectionResponse.getTenantDomain());
            }
            return introspectionResponse;
        } catch (ParseException ex) {
            throw new OAuthException("Error occurred when validating access token", ERROR_CODE_CEN_SC_10016, ex);
        }
    }

    private SSLSocketFactory buildSSLSocketFactory(KeyStore trustStore, boolean ignoreCertValidation)
            throws OAuthException {

        try {
            if (ignoreCertValidation) {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {

                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Trust validation disabled. Hence ignoring client validation.");
                        }
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {

                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Trust validation disabled. Hence ignoring server validation.");
                        }
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {

                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Trust validation disabled. Hence returning null.");
                        }
                        return null;
                    }
                }}, null);
                return sslContext.getSocketFactory();
            }
            return TLSUtils.createSSLSocketFactory(trustStore, TLSVersion.TLS_1_2);
        } catch (KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException | KeyManagementException e) {
            throw new OAuthException("Error occurred while building SSL socket factory.", ERROR_CODE_CEN_SC_10011, e);
        }
    }
}
