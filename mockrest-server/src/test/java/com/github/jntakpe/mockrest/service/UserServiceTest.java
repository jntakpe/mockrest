package com.github.jntakpe.mockrest.service;

import com.github.jntakpe.mockrest.domain.User;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.testng.annotations.Test;

import java.util.Optional;

import static com.github.jntakpe.mockrest.domain.Authority.ROLE_ADMIN;
import static com.github.jntakpe.mockrest.domain.Authority.ROLE_USER;
import static com.github.jntakpe.mockrest.service.UserApiServiceTest.USER_API_TABLE;
import static com.ninja_squad.dbsetup.Operations.sql;
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.testng.Assert.fail;

/**
 * Tests associés aux services de l'entité {@link User}
 *
 * @author jntakpe
 */
public class UserServiceTest extends AbstractServiceTestContext {

    public static final String USER_TABLE = "t_user";

    public static final String AUTHORITY_TABLE = "authority";

    public static final String USER_AUTHORITY_TABLE = "user_authority";

    public static final String ADMIN_USER = "adminuser";

    public static final String CLASSIC_USER = "classicuser";

    public static final String SECURITY_USER = "securityuser";

    public static final Long SECURITY_USER_ID = 99999L;

    @Autowired
    private UserService userService;

    public static Operation userOperations() {
        return sequenceOf(
                Operations.deleteAllFrom(USER_API_TABLE, USER_AUTHORITY_TABLE, USER_TABLE, AUTHORITY_TABLE),
                Operations
                        .insertInto(AUTHORITY_TABLE)
                        .columns("name")
                        .values(ROLE_USER)
                        .values(ROLE_ADMIN)
                        .build(),
                Operations.insertInto(USER_TABLE)
                        .columns("login", "first_name", "last_name", "email", "phone", "temporary")
                        .values(ADMIN_USER, "admin", "admin", "admin@gmail.com", "000000000000", false)
                        .values(CLASSIC_USER, "classic", "classic", "classic@gmail.com", "000000000000", false)
                        .build(),
                Operations.insertInto(USER_TABLE)
                        .columns("id", "login", "first_name", "last_name", "email", "phone", "temporary")
                        .values(SECURITY_USER_ID, SECURITY_USER, "security", "security", "security@gmail.com", "000000000000", false)
                        .build(),
                sql(buildInsertRequest(ADMIN_USER, ROLE_USER)),
                sql(buildInsertRequest(ADMIN_USER, ROLE_ADMIN)),
                sql(buildInsertRequest(CLASSIC_USER, ROLE_USER))
        );
    }

    private static String buildInsertRequest(String username, String authority) {
        return "insert into " + USER_AUTHORITY_TABLE + " (user_id, authority_name) " +
                "VALUES ((select id from " + USER_TABLE + " where login = '" + username + "'), '" + authority + "')";
    }

    @Override
    protected String getTable() {
        return USER_TABLE;
    }

    @Override
    public Operation operations() {
        return userOperations();
    }

    @Test
    public void findByLogin_shouldNotFind() {
        assertThat(userService.findByLogin("notfound")).isEmpty();
        dbSetupTracker.skipNextLaunch();
    }

    @Test
    public void findByLogin_shouldFind() {
        Optional<User> user = userService.findByLogin(ADMIN_USER);
        assertThat(user).isPresent();
        assertThat(Hibernate.isInitialized(user.get().getAuthorities())).isFalse();
        dbSetupTracker.skipNextLaunch();
    }

    @Test
    public void findByLogin_shouldFindIgnoringCase() {
        assertThat(userService.findByLogin(ADMIN_USER.toUpperCase())).isPresent();
        dbSetupTracker.skipNextLaunch();
    }

    @Test(expectedExceptions = UsernameNotFoundException.class)
    public void findByLoginWithAuthorities_shouldNotFind() {
        userService.findByLoginWithAuthorities("notfound");
        fail("Should throw UsernameNotFoundException");
        dbSetupTracker.skipNextLaunch();
    }

    @Test
    public void findByLoginWithAuthorities_shouldFind() {
        User user = userService.findByLoginWithAuthorities(ADMIN_USER);
        assertThat(Hibernate.isInitialized(user.getAuthorities())).isTrue();
        assertThat(user.getAuthorities().size()).isNotZero();
        dbSetupTracker.skipNextLaunch();
    }

    @Test
    public void findById_shouldFind() {
        Long adminId = jdbcTemplate.queryForObject("select id from " + USER_TABLE + " where login = '" + ADMIN_USER + "'", Long.class);
        assertThat(userService.findById(adminId));
        assertThat(userService.findById(adminId).getLogin()).isEqualTo(ADMIN_USER);
        dbSetupTracker.skipNextLaunch();
    }

    @Test
    public void createTemporary_shouldCreate() {
        User temporary = userService.createTemporary();
        assertThat(countRowsInTable(getTable())).isEqualTo(initCount + 1);
        assertThat(jdbcTemplate.queryForObject("select count(*) from t_user where login = ?", Long.class, temporary.getLogin())).isNotZero();
    }

    @Test
    public void createTemporary_shouldCreateTwice() {
        User temporary = userService.createTemporary();
        User second = userService.createTemporary();
        assertThat(resolveTmpSuffix(temporary.getLogin()) + 1).isEqualTo(resolveTmpSuffix(second.getLogin()));
    }

    @Test
    @WithUserDetails(ADMIN_USER)
    public void getCurrentUserOrCreateTemp_shouldReturnCurrent() {
        assertThat(userService.getCurrentUserOrCreateTemp().getLogin()).isEqualTo(ADMIN_USER);
    }

    @Test
    public void getCurrentUserOrCreateTemp_shouldCreateTmp() {
        assertThat(userService.getCurrentUserOrCreateTemp().getLogin()).startsWith(User.TMP_PREFIX);
    }

    private Integer resolveTmpSuffix(String login) {
        return Integer.parseInt(StringUtils.substringAfter(login, User.TMP_PREFIX));
    }
}
