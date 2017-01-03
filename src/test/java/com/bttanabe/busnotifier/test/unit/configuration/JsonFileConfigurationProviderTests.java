package com.bttanabe.busnotifier.test.unit.configuration;

import com.btanabe.busnotifier.configuration.ApplicationConfiguration;
import com.btanabe.busnotifier.configuration.TravelWindow;
import com.btanabe.busnotifier.configuration.providers.JsonFileConfigurationProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 12/26/16.
 */
@ContextConfiguration("classpath:spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JsonFileConfigurationProviderTests {

    @Autowired
    @Qualifier("testConfigurationProvider")
    private JsonFileConfigurationProvider testConfigurationProvider;

    @Resource(name = "expectedTravelWindowList")
    private List<TravelWindow> expectedTravelWindowList;

    @Autowired
    @Qualifier("expectedApplicationConfiguration")
    private ApplicationConfiguration expectedApplicationConfiguration;

    @Test
    public void shouldBeAbleToParseTravelWindowConfigurations() throws Exception {
        assertThat(testConfigurationProvider.getTravelWindowsToMonitor(), is(equalTo(expectedTravelWindowList)));
    }

    @Test
    public void shouldBeAbleToParseApplicationConfigurations() throws Exception {
        assertThat(testConfigurationProvider.getApplicationConfiguration(), is(equalTo(expectedApplicationConfiguration)));
    }
}
