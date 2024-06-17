package com.wso2.identity.asgardeo.central.service.rest.api.v1.impl;

import com.wso2.identity.asgardeo.central.service.rest.api.v1.HealthApiDelegate;
import org.springframework.http.ResponseEntity;

public class HealthApiImpl implements HealthApiDelegate {

    @Override
    public ResponseEntity getHealth() {

        return ResponseEntity.ok().build();
    }
}
