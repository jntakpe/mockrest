package com.github.jntakpe.mockrest.service;

import com.github.jntakpe.mockrest.MockrestServerApplication;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import static org.assertj.core.api.StrictAssertions.assertThat;

/**
 * Permet d'initialiser les tests sur la couche de service
 *
 * @author jntakpe
 */
@WebAppConfiguration
@TestExecutionListeners(WithSecurityContextTestExecutionListener.class)
@SpringApplicationConfiguration(classes = MockrestServerApplication.class)
public abstract class AbstractServiceTestContext extends AbstractTransactionalTestNGSpringContextTests {

    protected DbSetupTracker dbSetupTracker = new DbSetupTracker();

    protected int initCount;

    @BeforeClass
    public void setUp() {
        before();
    }

    @BeforeMethod
    public void before() {
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(jdbcTemplate.getDataSource()), operations());
        dbSetupTracker.launchIfNecessary(dbSetup);
        initCount = countRowsInTable(getTable());
        assertThat(initCount).isNotZero();
    }

    protected abstract String getTable();

    protected abstract Operation operations();

}
