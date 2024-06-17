package com.wso2.identity.asgardeo.central.service.core.constant;

public class Constants {

    public static class SQLQueries {

        public static final String ADD_TENANT = "INSERT INTO ASG_TENANT (UUID, TENANT_DOMAIN, USERNAME, CLUSTER) " +
                "VALUES (:UUID; ,:tenantDomain; ,:username; ,:cluster; );";
        public static final String DELETE_TENANT = "DELETE FROM ASG_TENANT WHERE UUID = :UUID;";
        public static final String GET_TENANT = "SELECT * FROM ASG_TENANT WHERE TENANT_DOMAIN = :tenantDomain;";
    }
}
