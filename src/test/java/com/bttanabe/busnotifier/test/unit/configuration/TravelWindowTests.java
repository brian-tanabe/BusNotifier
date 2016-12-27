package com.bttanabe.busnotifier.test.unit.configuration;

import com.btanabe.busnotifier.configuration.TravelWindow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 12/22/16.
 */
@ContextConfiguration("classpath:spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TravelWindowTests {

    @Autowired
    @Qualifier("seventeenThirtyToTwentyThreeThirtyTravelWindow")
    private TravelWindow seventeenThirtyToTwentyThreeThirtyTravelWindow;

    @Autowired
    @Qualifier("seventeenThirtyToTwoThirtyTravelWindow")
    private TravelWindow seventeenThirtyToTwoThirtyTravelWindow;

    @Test
    public void shouldKnowTheWindowIsOpenWhenTheTimeIsTheSameAsTheStartTime() {
        assertThat(seventeenThirtyToTwentyThreeThirtyTravelWindow.isTimeWithinWindow(createTestLocalDateTime(17, 30)), is(true));
    }

    @Test
    public void shouldKnowTheWindowIsOpenWhenTheTimeIsTheSameAsTheEndTime() {
        assertThat(seventeenThirtyToTwentyThreeThirtyTravelWindow.isTimeWithinWindow(createTestLocalDateTime(23, 30)), is(true));
    }

    @Test
    public void shouldKnowTheWindowIsClosedWhenTheTimeIsBeforeTheStartTime() {
        assertThat(seventeenThirtyToTwentyThreeThirtyTravelWindow.isTimeWithinWindow(createTestLocalDateTime(17, 29)), is(false));
    }

    @Test
    public void shouldKnowTheWindowIsClosedWhenTheTimeIsAfterTheEndTime() {
        assertThat(seventeenThirtyToTwentyThreeThirtyTravelWindow.isTimeWithinWindow(createTestLocalDateTime(23, 31)), is(false));
    }

    @Test
    public void shouldKnowTheWindowIsOpenWhenTheTimeIsWellWithinTheBounds() {
        assertThat(seventeenThirtyToTwentyThreeThirtyTravelWindow.isTimeWithinWindow(createTestLocalDateTime(20, 00)), is(true));
    }

    @Test
    public void shouldKnowToSendNotificationsWhenTheTimeToArrivalIsLessThanTheOffsetTime() {
        assertThat(seventeenThirtyToTwentyThreeThirtyTravelWindow.shouldSendNotification(seventeenThirtyToTwentyThreeThirtyTravelWindow.getMinutesBeforeArrivalToStartNotifying() - 1), is(true));
    }

    @Test
    public void shouldKnowToSendNotificationsWhenTheTimeToArrivalIsTheSameAsTheOffsetTime() {
        assertThat(seventeenThirtyToTwentyThreeThirtyTravelWindow.shouldSendNotification(seventeenThirtyToTwentyThreeThirtyTravelWindow.getMinutesBeforeArrivalToStartNotifying()), is(true));
    }

    @Test
    public void shouldKnowToNotSendNotificationsWhenTheTimeToArrivalIsGreaterThanTheOffsetTime() {
        assertThat(seventeenThirtyToTwentyThreeThirtyTravelWindow.shouldSendNotification(seventeenThirtyToTwentyThreeThirtyTravelWindow.getMinutesBeforeArrivalToStartNotifying() + 1), is(false));
    }

    @Test
    public void shouldKnowTheWindowIsOpenWhenTheEndTimeFallsOnTheNextDayAndTheCurrentTimeIsOnTheStartingDay() {
        assertThat(seventeenThirtyToTwoThirtyTravelWindow.isTimeWithinWindow(createTestLocalDateTime(18, 0)), is(true));
    }

    @Test
    public void shouldKnowTheWindowIsOpenWhenTheEndTimeFallsOnTheNextDayAndTheCurrentTimeIsOnTheFollowingDay() {
        assertThat(seventeenThirtyToTwoThirtyTravelWindow.isTimeWithinWindow(createTestLocalDateTime(1, 0).plusDays(1L)), is(true));
    }

    @Test
    public void shouldKnowTheWindowIsClosedWhenTheEndTimeFallsOnTheNextDayAndTheCurrentTimeIsAfterItsClosed() {
        assertThat(seventeenThirtyToTwoThirtyTravelWindow.isTimeWithinWindow(createTestLocalDateTime(2, 31)), is(false));
    }

    @Test
    public void shouldKnowTheWindowIsClosedWhenTheEndTimeFallsOnTheNextDayAndTheCurrentTimeIsBeforeItsClosed() {
        assertThat(seventeenThirtyToTwoThirtyTravelWindow.isTimeWithinWindow(createTestLocalDateTime(7, 30)), is(false));
    }

    private LocalDateTime createTestLocalDateTime(int hour, int minute) {
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, minute));
    }
}
