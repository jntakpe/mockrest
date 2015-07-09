package com.github.jntakpe.mockrest.service;

import com.github.jntakpe.mockrest.MockrestServerApplication;
import com.github.jntakpe.mockrest.domain.Authority;
import com.github.jntakpe.mockrest.domain.User;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.util.Optional;

import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.testng.Assert.fail;

/**
 * @author jntakpe
 */
@WebAppConfiguration
@SpringApplicationConfiguration(classes = MockrestServerApplication.class)
public class UserServiceTests extends AbstractTestNGSpringContextTests {

    public static final String USER_TABLE = "api_user";

    public static final String AUTHORITY_TABLE = "authority";

    public static final String USER_AUTHORITY_TABLE = "user_authority";

    public static final String ADMIN_USER = "adminuser";

    public static final String CLASSIC_USER = "classicuser";

    private DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @BeforeClass
    public void setUp() {
        Operation operation = sequenceOf(
                Operations.deleteAllFrom(USER_TABLE, AUTHORITY_TABLE),
                Operations
                        .insertInto(AUTHORITY_TABLE)
                        .columns("name")
                        .values(Authority.ROLE_USER)
                        .values(Authority.ROLE_ADMIN)
                        .build(),
                Operations.insertInto(USER_TABLE)
                        .columns("id", "login", "first_name", "last_name", "email", "phone")
                        .values(1, ADMIN_USER, "admin", "admin", "admin@gmail.com", "000000000000")
                        .values(2, CLASSIC_USER, "classic", "classic", "classic@gmail.com", "000000000000")
                        .build(),
                Operations
                        .insertInto(USER_AUTHORITY_TABLE)
                        .columns("user_id", "authority_name")
                        .values(1, Authority.ROLE_ADMIN)
                        .values(1, Authority.ROLE_USER)
                        .values(2, Authority.ROLE_USER)
                        .build()
        );
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetupTracker.launchIfNecessary(dbSetup);
    }

    @Test
    public void findByLogin_shouldNotFind() {
        assertThat(userService.findByLogin("notfound")).isEmpty();
    }

    @Test
    public void findByLogin_shouldFind() {
        Optional<User> user = userService.findByLogin(ADMIN_USER);
        assertThat(user).isPresent();
        assertThat(Hibernate.isInitialized(user.get().getAuthorities())).isFalse();
    }

    @Test
    public void findByLogin_shouldFindIgnoringCase() {
        assertThat(userService.findByLogin(ADMIN_USER.toUpperCase())).isPresent();
    }

    @Test(expectedExceptions = UsernameNotFoundException.class)
    public void findByLoginWithAuthorities_shouldNotFind() {
        userService.findByLoginWithAuthorities("notfound");
        fail("Should throw UsernameNotFoundException");
    }

    @Test
    public void findByLoginWithAuthorities_shouldFind() {
        User user = userService.findByLoginWithAuthorities(ADMIN_USER);
        assertThat(Hibernate.isInitialized(user.getAuthorities())).isTrue();
        assertThat(user.getAuthorities().size()).isNotZero();
    }

}
