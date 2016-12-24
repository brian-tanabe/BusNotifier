package com.bttanabe.busnotifier.test.unit.utilities;

import com.btanabe.busnotifier.utilities.TimeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 12/24/16.
 */
@ContextConfiguration("classpath*:spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TimeHelperTests {

    private static final long MINUTE_ONE_IN_MILLIS = 60 * 1000;
    private static final long MINUTE_TWO_IN_MILLIS = 120 * 1000;

    private static final long ONE_MINUTE = 1;
    private static final long ZERO_MINUTES = 0;
    private static final long ONE_MINUTE_AGO = -1;

    @Test
    public void shouldBeAbleToDetermineTheDifferenceBetweenTwoUniqueTimes() {
        assertThat(TimeHelper.getTimeDifferenceInMinutes(MINUTE_ONE_IN_MILLIS, MINUTE_TWO_IN_MILLIS), is(equalTo(ONE_MINUTE)));
    }

    @Test
    public void shouldBeAbleToDetermineTheDifferenceBetweenTwoNonUniqueTimes() {
        assertThat(TimeHelper.getTimeDifferenceInMinutes(MINUTE_ONE_IN_MILLIS, MINUTE_ONE_IN_MILLIS), is(equalTo(ZERO_MINUTES)));
    }

    @Test
    public void shouldBeAbleToDetermineTheDifferenceWhenTheResultIsInThePast() {
        assertThat(TimeHelper.getTimeDifferenceInMinutes(MINUTE_TWO_IN_MILLIS, MINUTE_ONE_IN_MILLIS), is(equalTo(ONE_MINUTE_AGO)));
    }
}
