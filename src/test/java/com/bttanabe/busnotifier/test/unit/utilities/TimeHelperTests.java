package com.bttanabe.busnotifier.test.unit.utilities;

import com.btanabe.busnotifier.utilities.TimeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 12/24/16.
 */
@ContextConfiguration("classpath*:spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TimeHelperTests {

    private static final LocalDateTime MINUTE_ONE = LocalDateTime.of(2016, 12, 25, 12, 1);
    private static final LocalDateTime MINUTE_TWO = LocalDateTime.of(2016, 12, 25, 12, 2);

    private static final long ONE_MINUTE = 1;
    private static final long ZERO_MINUTES = 0;
    private static final long ONE_MINUTE_AGO = -1;

    @Test
    public void shouldBeAbleToDetermineTheDifferenceBetweenTwoUniqueTimes() {
        assertThat(TimeHelper.getTimeDifferenceInMinutes(MINUTE_ONE, MINUTE_TWO), is(equalTo(ONE_MINUTE)));
    }

    @Test
    public void shouldBeAbleToDetermineTheDifferenceBetweenTwoNonUniqueTimes() {
        assertThat(TimeHelper.getTimeDifferenceInMinutes(MINUTE_ONE, MINUTE_ONE), is(equalTo(ZERO_MINUTES)));
    }

    @Test
    public void shouldBeAbleToDetermineTheDifferenceWhenTheResultIsInThePast() {
        assertThat(TimeHelper.getTimeDifferenceInMinutes(MINUTE_TWO, MINUTE_ONE), is(equalTo(ONE_MINUTE_AGO)));
    }
}
