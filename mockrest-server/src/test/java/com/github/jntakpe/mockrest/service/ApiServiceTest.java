package com.github.jntakpe.mockrest.service;

import com.github.jntakpe.mockrest.domain.Api;
import com.github.jntakpe.mockrest.repository.ApiRepository;
import com.github.jntakpe.mockrest.repository.UserApiRepository;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.testng.annotations.Test;

import static com.github.jntakpe.mockrest.service.UserServiceTest.SECURITY_USER;
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

    public static final String GMAP_API = "gmap_api";

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private ApiService apiService;

    @Autowired
    private UserApiRepository userApiRepository;

    public static Operation apiOperations() {
        return sequenceOf(
                Operations.deleteAllFrom(API_TABLE),
                Operations
                        .insertInto(API_TABLE)
                        .columns("name")
                        .values(TWITTER_API)
                        .values(GITHUB_API)
                        .values(GMAP_API)
                        .build()
        );
    }

    @Override
    protected String getTable() {
        return API_TABLE;
    }

    @Override
    protected Operation operations() {
        return UserApiServiceTest.userApiOperations();
    }

    @Test
    @WithUserDetails(SECURITY_USER)
    public void createTest_shouldCreate() {
        Api api = new Api();
        api.setName("newAPI");
        Api saved = apiService.create(api);
        assertThat(countRowsInTable(getTable())).isEqualTo(++initCount);
        assertThat(saved).isNotNull();
    }

    @Test
    public void removeTest_shouldRemove() {
        Long githubApiId = getIdWithName(GITHUB_API);
        Long userApiId = apiRepository.findOne(githubApiId).getUserApis().stream().findAny().get().getId();
        apiService.delete(githubApiId);
        assertThat(apiRepository.findOne(githubApiId)).isNull();
        assertThat(userApiRepository.findOne(userApiId)).isNull();
    }

    private Long getIdWithName(String name) {
        return jdbcTemplate.queryForObject("select id from " + API_TABLE + " where name='" + name + "'", Long.class);
    }

}
