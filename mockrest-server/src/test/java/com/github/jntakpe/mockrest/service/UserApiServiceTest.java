package com.github.jntakpe.mockrest.service;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static com.github.jntakpe.mockrest.domain.Authority.ROLE_ADMIN;
import static com.github.jntakpe.mockrest.domain.Authority.ROLE_USER;
import static com.github.jntakpe.mockrest.service.ApiServiceTest.*;
import static com.github.jntakpe.mockrest.service.UserServiceTest.*;
import static com.ninja_squad.dbsetup.Operations.sql;
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;

/**
 * Tests des services associés à l'entité {@link UserApiServiceTest}
 *
 * @author jntakpe
 */
public class UserApiServiceTest extends AbstractServiceTestContext {

    public static final String USER_API_TABLE = "user_api";

    @Autowired
    private UserApiService userApiService;

    public static Operation userApiOperations() {
        userOperations();
        ApiServiceTest.apiOperations();
        return sequenceOf(
                Operations.deleteAllFrom(USER_API_TABLE),
                sql(buildInsertRequest(ADMIN_USER, ROLE_ADMIN, TWITTER_API)),
                sql(buildInsertRequest(ADMIN_USER, ROLE_USER, GITHUB_API)),
                sql(buildInsertRequest(CLASSIC_USER, ROLE_ADMIN, GITHUB_API))
        );
    }

    private static String buildInsertRequest(String username, String authority, String apiname) {
        return "insert into " + USER_API_TABLE + " (user_id, authority_name, api_id) " +
                "VALUES ((select id from " + USER_TABLE + " where login = '" + username + "')," +
                "'" + authority + "'," +
                "(select id from " + API_TABLE + " where name = '" + apiname + "'))";
    }

    @Override
    protected String getTable() {
        return USER_API_TABLE;
    }

    @Override
    protected Operation operations() {
        return userApiOperations();
    }

    @Test
    public void createTest_shouldCreate() {

    }
}
