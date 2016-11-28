package com.bttanabe.busnotifier.test.integration;

import com.btanabe.busnotifier.scheduleprovider.AbstractOneBusAwayApiInterface;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;

/**
 * Created by Brian on 5/15/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(AbstractOneBusAwayApiInterface.class)
@PowerMockIgnore("javax.management.*")
@ContextConfiguration("classpath:spring-configuration/unit-testing-configuration.xml")
public abstract class MockWebRequestTaskBase {

    @Autowired
    @Qualifier("mockStaticWebRequestFactory")
    protected Object mockWebRequestCreation;

    private TestContextManager testContextManager;

    @Before
    public void setupSpringAutowiringContexts() throws Exception {
        // Provides the same functionality as @RunWith(SpringJUnit4ClassRunner.class):
        this.testContextManager = new TestContextManager(getClass());
        this.testContextManager.prepareTestInstance(this);
    }
}
