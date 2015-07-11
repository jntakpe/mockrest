package com.github.jntakpe.mockrest.service;

import com.github.jntakpe.mockrest.config.security.SecurityUtils;
import com.github.jntakpe.mockrest.domain.Api;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.testng.annotations.Test;

import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static org.assertj.core.api.StrictAssertions.assertThat;

/**
 * Tests associés aux services de l'entité {@link Api}
 *
 * @author jntakpe
 */

public class ApiServiceTest extends AbstractServiceTestContext {

    //FIXME use super class to bootstrap tests

    public static final String API_TABLE = "api";

    public static final String TWITTER_API = "twitter_api";

    public static final String GITHUB_API = "github_api";

    @Autowired
    private ApiService apiService;

    public static Operation apiOperations() {
        UserServiceTest.userOperations();
        return sequenceOf(
                Operations.deleteAllFrom(API_TABLE),
                Operations
                        .insertInto(API_TABLE)
                        .columns("name")
                        .values(TWITTER_API)
                        .values(GITHUB_API)
                        .build()
        );
    }

    @Override
    protected String getTable() {
        return API_TABLE;
    }

    @Override
    protected Operation operations() {
        return apiOperations();
    }

    @Test
    @WithUserDetails(UserServiceTest.ADMIN_USER)
    public void createTest_shouldCreate() throws Exception {
        assertThat(SecurityUtils.getCurrentUser()).isPresent();
    }

}
