package com.wso2.identity.asgardeo.central.service.rest.api.v1;

import com.wso2.identity.asgardeo.central.service.rest.api.v1.model.Error;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Controller
@RequestMapping("${openapi.asgardeoCentralService.base-path:/api/asgardeo/central-service/v1}")
public class HealthApiController implements HealthApi {

    private final HealthApiDelegate delegate;

    public HealthApiController(@Autowired(required = false) HealthApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new HealthApiDelegate() {});
    }

    @Override
    public HealthApiDelegate getDelegate() {
        return delegate;
    }

}
