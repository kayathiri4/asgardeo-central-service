package com.wso2.identity.asgardeo.central.service.rest.api.v1.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * TenantRegistrationRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class TenantRegistrationRequest {

  private String domain;

  private String username;

  private String cluster;

  /**
   * Default constructor
   * @deprecated Use {@link TenantRegistrationRequest#TenantRegistrationRequest(String, String)}
   */
  @Deprecated
  public TenantRegistrationRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TenantRegistrationRequest(String domain, String username) {
    this.domain = domain;
    this.username = username;
  }

  public TenantRegistrationRequest domain(String domain) {
    this.domain = domain;
    return this;
  }

  /**
   * Tenant domain of the tenant.
   * @return domain
  */
  @NotNull 
  @Schema(name = "domain", example = "wso2.com", description = "Tenant domain of the tenant.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("domain")
  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public TenantRegistrationRequest username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Username of the tenant admin.
   * @return username
  */
  @NotNull 
  @Schema(name = "username", example = "user@gmail.com", description = "Username of the tenant admin.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public TenantRegistrationRequest cluster(String cluster) {
    this.cluster = cluster;
    return this;
  }

  /**
   * Cluster name of the tenant.
   * @return cluster
  */
  
  @Schema(name = "cluster", example = "cluster-1", description = "Cluster name of the tenant.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cluster")
  public String getCluster() {
    return cluster;
  }

  public void setCluster(String cluster) {
    this.cluster = cluster;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TenantRegistrationRequest tenantRegistrationRequest = (TenantRegistrationRequest) o;
    return Objects.equals(this.domain, tenantRegistrationRequest.domain) &&
        Objects.equals(this.username, tenantRegistrationRequest.username) &&
        Objects.equals(this.cluster, tenantRegistrationRequest.cluster);
  }

  @Override
  public int hashCode() {
    return Objects.hash(domain, username, cluster);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TenantRegistrationRequest {\n");
    sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    cluster: ").append(toIndentedString(cluster)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

