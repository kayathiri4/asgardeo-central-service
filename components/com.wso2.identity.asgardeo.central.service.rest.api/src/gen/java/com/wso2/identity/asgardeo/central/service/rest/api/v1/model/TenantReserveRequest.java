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
 * TenantReserveRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class TenantReserveRequest {

  private String domain;

  private String domainId;

  private String username;

  private String cluster;

  /**
   * Default constructor
   * @deprecated Use {@link TenantReserveRequest#TenantReserveRequest(String, String, String)}
   */
  @Deprecated
  public TenantReserveRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TenantReserveRequest(String domain, String domainId, String username) {
    this.domain = domain;
    this.domainId = domainId;
    this.username = username;
  }

  public TenantReserveRequest domain(String domain) {
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

  public TenantReserveRequest domainId(String domainId) {
    this.domainId = domainId;
    return this;
  }

  /**
   * Domain ID of the tenant.
   * @return domainId
  */
  @NotNull 
  @Schema(name = "domainId", example = "2345-3445-3243-2342", description = "Domain ID of the tenant.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("domainId")
  public String getDomainId() {
    return domainId;
  }

  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }

  public TenantReserveRequest username(String username) {
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

  public TenantReserveRequest cluster(String cluster) {
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
    TenantReserveRequest tenantReserveRequest = (TenantReserveRequest) o;
    return Objects.equals(this.domain, tenantReserveRequest.domain) &&
        Objects.equals(this.domainId, tenantReserveRequest.domainId) &&
        Objects.equals(this.username, tenantReserveRequest.username) &&
        Objects.equals(this.cluster, tenantReserveRequest.cluster);
  }

  @Override
  public int hashCode() {
    return Objects.hash(domain, domainId, username, cluster);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TenantReserveRequest {\n");
    sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
    sb.append("    domainId: ").append(toIndentedString(domainId)).append("\n");
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

