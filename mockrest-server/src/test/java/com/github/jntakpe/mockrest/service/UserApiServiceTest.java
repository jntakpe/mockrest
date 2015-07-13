package com.github.jntakpe.mockrest.service;

import com.github.jntakpe.mockrest.domain.Api;
import com.github.jntakpe.mockrest.domain.UserApi;
import com.github.jntakpe.mockrest.repository.ApiRepository;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.testng.annotations.Test;

import static com.github.jntakpe.mockrest.domain.Authority.ROLE_ADMIN;
import static com.github.jntakpe.mockrest.domain.Authority.ROLE_USER;
import static com.github.jntakpe.mockrest.service.ApiServiceTest.*;
import static com.github.jntakpe.mockrest.service.UserServiceTest.*;
import static com.ninja_squad.dbsetup.Operations.sql;
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests des services associés à l'entité {@link UserApiServiceTest}
 *
 * @author jntakpe
 */
public class UserApiServiceTest extends AbstractServiceTestContext {

    public static final String USER_API_TABLE = "user_api";

    @Autowired
    private UserApiService userApiService;

    @Autowired
    private ApiRepository apiRepository;

    public static Operation userApiOperations() {
        return sequenceOf(
                Operations.deleteAllFrom(USER_API_TABLE),
                userOperations(),
                apiOperations(),
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
    public void createTest_shouldCreateWithTmpUser() {
        testContent();
    }

    @Test
    @WithUserDetails(SECURITY_USER)
    public void createTest_shouldCreateWithExistingUser() {
        testContent();
    }

    private void testContent() {
        Api api = apiRepository.findOne(findApiIdByName(GMAP_API));
        Api save = apiRepository.save(api);
        UserApi userApi = userApiService.create(save);
        assertThat(initCount + 1).isEqualTo(countRowsInTable(getTable()));
        assertThat(userApi.getUser()).isNotNull();
        assertThat(userApi.getApi()).isNotNull();
        assertThat(userApi.getAuthority()).isNotNull();
    }

    private Long findApiIdByName(String name) {
        return jdbcTemplate.queryForObject("select id from " + API_TABLE + " where name='" + name + "'", Long.class);
    }
}
