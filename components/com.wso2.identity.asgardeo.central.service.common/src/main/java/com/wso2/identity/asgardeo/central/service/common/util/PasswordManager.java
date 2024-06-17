package com.wso2.identity.asgardeo.central.service.common.util;

import com.wso2.identity.asgardeo.central.service.common.exception.FileHandlingException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorCodes.ERROR_CODE_CEN_SC_10003;
import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorCodes.ERROR_CODE_CEN_SC_10004;
import static com.wso2.identity.asgardeo.central.service.common.constant.ErrorCodes.ERROR_CODE_CEN_SC_10005;

/**
 * Utility class for password handling.
 */
public class PasswordManager {

    private static final Log LOG = LogFactory.getLog(PasswordManager.class);

    private static final Map<String, byte[]> passwordMap = new HashMap<>();

    /**
     * Get the password from the given file path.
     *
     * @param filePath File path of the password file.
     * @return Password.
     */
    public static String getPasswordFromFile(String filePath) {

        if (StringUtils.isEmpty(filePath)) {
            throw new FileHandlingException("Password file path is null or empty", ERROR_CODE_CEN_SC_10003);
        }

        if (passwordMap.containsKey(filePath)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Password already exists in the cache. Returning the cached password.");
            }
            return new String(passwordMap.get(filePath), StandardCharsets.UTF_8).trim();
        }

        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                throw new FileHandlingException("Password file does not exist: " + filePath, ERROR_CODE_CEN_SC_10004);
            }
            byte[] password = Files.readAllBytes(path);
            passwordMap.put(filePath, password);
            return new String(password, StandardCharsets.UTF_8).trim();
        } catch (IOException e) {
            throw new FileHandlingException("Error while reading password file: " + filePath, ERROR_CODE_CEN_SC_10005,
                    e);
        }
    }
}
