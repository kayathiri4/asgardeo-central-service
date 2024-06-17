package com.wso2.identity.asgardeo.central.service.rest.api.v1;

import com.wso2.identity.asgardeo.central.service.rest.api.v1.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

/**
 * A delegate to be called by the {@link HealthApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface HealthApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /tenant/health : Check health of the API.
     * This API is used to check health of the API. 
     *
     * @return OK (status code 200)
     *         or Internal Server Error (status code 500)
     * @see HealthApi#getHealth
     */
    default ResponseEntity<Void> getHealth() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
