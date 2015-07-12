package com.github.jntakpe.mockrest.service;

import com.github.jntakpe.mockrest.MockrestServerApplication;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import javax.sql.DataSource;

import static org.assertj.core.api.StrictAssertions.assertThat;

/**
 * Permet d'initialiser les tests sur la couche de service
 *
 * @author jntakpe
 */
@WebAppConfiguration
@TestExecutionListeners(WithSecurityContextTestExecutionListener.class)
@SpringApplicationConfiguration(classes = MockrestServerApplication.class)
public abstract class AbstractServiceTestContext extends AbstractTestNGSpringContextTests {

    @Autowired
    protected DataSource dataSource;

    protected JdbcTemplate jdbcTemplate;

    protected DbSetupTracker dbSetupTracker = new DbSetupTracker();

    protected Long initCount;

    @BeforeClass
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        before();
    }

    @BeforeMethod
    public void before() {
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operations());
        dbSetupTracker.launchIfNecessary(dbSetup);
        initCount = count();
        assertThat(initCount).isNotZero();
    }

    protected abstract String getTable();

    protected abstract Operation operations();

    public Long count() {
        return jdbcTemplate.queryForObject("select count(*) from " + getTable(), Long.class);
    }
}
