/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.identity.asgardeo.central.service.api.server;

import com.fasterxml.jackson.databind.Module;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.Serial;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.wso2.identity.asgardeo.central.service.api.server",
        "com.wso2.identity.asgardeo.central.service.rest.api.v1"
})
public class SpringBootApp implements CommandLineRunner {

    private static final Log LOG = LogFactory.getLog(SpringBootApp.class);


    public static void main(String[] args) {

        LOG.info("Starting the Asgardeo Central Service API Server.");
        SpringApplication.run(SpringBootApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (args.length > 0 && args[0].equals("exitcode")) {
            throw new SpringBootApp.ExitException();
        }
    }

    /**
     * This is to support the serialization of {@link org.openapitools.jackson.nullable.JsonNullable} objects.
     *
     * @return {@link Module} object.
     */
    @Bean
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }

    /**
     * Exception to throw when we want to exit the application with a specific exit code.
     */
    static class ExitException extends RuntimeException implements ExitCodeGenerator {

        @Serial
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }
    }
}
