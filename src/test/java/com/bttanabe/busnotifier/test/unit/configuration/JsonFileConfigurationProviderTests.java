package com.bttanabe.busnotifier.test.unit.configuration;

import com.btanabe.busnotifier.configuration.TravelWindow;
import com.btanabe.busnotifier.configuration.providers.JsonFileConfigurationProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
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

    @Value("classpath:/test-configurations/test-travel-window-configuration.json")
    private File testJsonConfigurationFile;

    @Resource(name = "expectedTravelWindowList")
    private List<TravelWindow> expectedTravelWindowList;

    @Test
    public void shouldBeAbleToParseTravelWindowConfigurations() throws Exception {
        JsonFileConfigurationProvider jsonFileConfigurationProvider = new JsonFileConfigurationProvider(testJsonConfigurationFile);
        assertThat(jsonFileConfigurationProvider.getTravelWindowsToMonitor(), is(equalTo(expectedTravelWindowList)));
    }
}
