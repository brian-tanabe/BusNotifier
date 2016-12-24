package com.bttanabe.busnotifier.test.unit.model.comparators;

import com.btanabe.busnotifier.model.comparators.DepartureTimeHelper;
import com.btanabe.busnotifier.model.types.ArrivalsAndDepartures;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 12/23/16.
 */
@ContextConfiguration("classpath:spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DepartureTimeHelperTests {

    @Test
    public void shouldReturnTheScheduledDepartureTimeWhenAPredictedTimeIsNotPresent() {
        ArrivalsAndDepartures arrivalsAndDepartures = new ArrivalsAndDepartures();
        arrivalsAndDepartures.setPredictedDepartureTime(0L);
        arrivalsAndDepartures.setScheduledDepartureTime(4L);

        assertThat(DepartureTimeHelper.getDepartureTime(arrivalsAndDepartures), is(equalTo(4L)));
    }

    @Test
    public void shouldReturnThePredictedTimeIfPresent() {
        ArrivalsAndDepartures arrivalsAndDepartures = new ArrivalsAndDepartures();
        arrivalsAndDepartures.setPredictedDepartureTime(3L);
        arrivalsAndDepartures.setScheduledDepartureTime(4L);

        assertThat(DepartureTimeHelper.getDepartureTime(arrivalsAndDepartures), is(equalTo(3L)));
    }
}
