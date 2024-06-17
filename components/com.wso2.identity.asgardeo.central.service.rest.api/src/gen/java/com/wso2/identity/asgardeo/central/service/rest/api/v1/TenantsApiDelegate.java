package com.wso2.identity.asgardeo.central.service.rest.api.v1;

import com.wso2.identity.asgardeo.central.service.rest.api.v1.model.Error;
import com.wso2.identity.asgardeo.central.service.rest.api.v1.model.TenantReserveRequest;
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
 * A delegate to be called by the {@link TenantsApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface TenantsApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /tenant : Reserve tenant by new user in the sign up.
     * This API provides the capability to create tenants from the new asgardeo users at the sign up. 
     *
     * @param tenantReserveRequest This represents the tenant to be created. (required)
     * @return Item Created (status code 201)
     *         or Invalid Input Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Resource Forbidden (status code 403)
     *         or Element Already Exists (status code 409)
     *         or Internal Server Error (status code 500)
     * @see TenantsApi#addTenantForRegistration
     */
    default ResponseEntity<Void> addTenantForRegistration(TenantReserveRequest tenantReserveRequest) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /tenant : Delete tenant.
     * This API provides the capability to delete tenant. 
     *
     * @param tenantId Tenant ID of the tenant. (required)
     * @return Item Deleted (status code 204)
     *         or Invalid Input Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Resource Forbidden (status code 403)
     *         or Tenant Not Found (status code 404)
     *         or Internal Server Error (status code 500)
     * @see TenantsApi#deleteTenant
     */
    default ResponseEntity<Void> deleteTenant(String tenantId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /tenant/check-domain : Check domain Existence.
     * Check the tenant existence. 
     *
     * @param domain Domain name of the user&#39;s current tenant. (required)
     * @param username Base64 encoded username of the tenant admin. (required)
     * @return Tenant Exist (status code 200)
     *         or Invalid Input Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Resource Forbidden (status code 403)
     *         or Tenant Not Found (status code 404)
     *         or Internal Server Error (status code 500)
     * @see TenantsApi#isDomainExist
     */
    default ResponseEntity<Void> isDomainExist(String domain,
        String username) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
