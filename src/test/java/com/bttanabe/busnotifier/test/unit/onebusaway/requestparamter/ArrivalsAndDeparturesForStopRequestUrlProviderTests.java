package com.bttanabe.busnotifier.test.unit.onebusaway.requestparamter;

import com.btanabe.busnotifier.onebusaway.requestparamter.ArrivalsAndDeparturesForStopRequestUrlProvider;
import com.btanabe.busnotifier.secrets.KeyProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 12/2/16.
 */
@ContextConfiguration("classpath*:spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ArrivalsAndDeparturesForStopRequestUrlProviderTests {
    private final String TEST_STOP_ID = "1_12340";

    @Autowired
    @Qualifier("testOneBusAwayKeyProvider")
    private KeyProvider testKeyProvider;

    @Test
    public void shouldBeAbleToConstructWellFormattedRequestUrl() {
        ArrivalsAndDeparturesForStopRequestUrlProvider provider = new ArrivalsAndDeparturesForStopRequestUrlProvider(TEST_STOP_ID);
        assertThat(provider.getRequestUrl(testKeyProvider), is(equalTo("http://api.pugetsound.onebusaway.org/api/where/arrivals-and-departures-for-stop/1_12340.json?key=TEST")));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenKeyProviderIsNull() {
        new ArrivalsAndDeparturesForStopRequestUrlProvider(TEST_STOP_ID).getRequestUrl(null);
    }
}
