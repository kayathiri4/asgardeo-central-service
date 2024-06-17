/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.common.util;

import com.wso2.identity.asgardeo.central.service.common.exception.KeyStoreHandlingException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorCodes.ERROR_CODE_CEN_SC_10008;
import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorCodes.ERROR_CODE_CEN_SC_10009;
import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorCodes.ERROR_CODE_CEN_SC_10010;

public class KeyStoreManager {

    private static final Log LOG = LogFactory.getLog(KeyStoreManager.class);

    private static final Map<String, KeyStore> keyStoreMap = new HashMap<>();

    /**
     * Get the keystore using the given file path, password and type.
     *
     * @param filePath File path of the keystore.
     * @param password Password of the keystore.
     * @param type     Type of the keystore.
     * @return Keystore ({@link KeyStore}).
     * @throws KeyStoreHandlingException If an error occurs while getting the keystore.
     */
    public static KeyStore getKeystore(String filePath, char[] password, String type) throws KeyStoreHandlingException {

        if (StringUtils.isEmpty(filePath)) {
            throw new KeyStoreHandlingException("Invalid keystore file path provided.", ERROR_CODE_CEN_SC_10008);
        }

        if (keyStoreMap.containsKey(filePath)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Keystore already exists in the cache. Returning the cached keystore.");
            }
            return keyStoreMap.get(filePath);
        }

        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new KeyStoreHandlingException("Keystore file does not exist at path: " + filePath,
                    ERROR_CODE_CEN_SC_10009);
        }

        try (InputStream in = Files.newInputStream(path)) {
            KeyStore keystore = KeyStore.getInstance(type);
            keystore.load(in, password);
            keyStoreMap.put(filePath, keystore);
            return keystore;
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            throw new KeyStoreHandlingException("Error occurred while reading the key store.",
                    ERROR_CODE_CEN_SC_10010, e);
        }
    }
}
