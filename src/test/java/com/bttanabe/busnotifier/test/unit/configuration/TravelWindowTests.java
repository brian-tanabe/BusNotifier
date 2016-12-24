package com.bttanabe.busnotifier.test.unit.configuration;

import com.btanabe.busnotifier.configuration.RouteAtStopToMonitor;
import com.btanabe.busnotifier.configuration.TravelWindow;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.bttanabe.busnotifier.test.constants.TestRouteIds.ROUTE_1_100009;
import static com.bttanabe.busnotifier.test.constants.TestStopIds.STOP_1_12340;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 12/22/16.
 */
@ContextConfiguration("classpath:spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TravelWindowTests {

    private static RouteAtStopToMonitor testRouteAtStopToMonitor = new RouteAtStopToMonitor(ROUTE_1_100009, STOP_1_12340, "E Madison St & 25th Ave E");

    private static final Long testWindowStartTime = 5L;
    private static final Long testWindowEndTime = 10L;
    private static final Long testTimeBeforeArrivalToStartNotifying = 3L;

    private static TravelWindow window;

    @BeforeClass
    public static void createTestTravelWindow() {
        window = new TravelWindow(testRouteAtStopToMonitor, testWindowStartTime, testWindowEndTime, testTimeBeforeArrivalToStartNotifying);
    }

    @Test
    public void shouldKnowTheWindowIsOpenWhenTheTimeIsTheSameAsTheStartTime() {
        assertThat(window.isTimeWithinWindow(testWindowStartTime), is(true));
    }

    @Test
    public void shouldKnowTheWindowIsOpenWhenTheTimeIsTheSameAsTheEndTime() {
        assertThat(window.isTimeWithinWindow(testWindowEndTime), is(true));
    }

    @Test
    public void shouldKnowTheWindowIsClosedWhenTheTimeIsBeforeTheStartTime() {
        assertThat(window.isTimeWithinWindow(testWindowStartTime - 1), is(false));
    }

    @Test
    public void shouldKnowTheWindowIsClosedWhenTheTimeIsAfterTheEndTime() {
        assertThat(window.isTimeWithinWindow(testWindowEndTime + 1), is(false));
    }

    @Test
    public void shouldKnowToSendNotificationsWhenTheTimeToArrivalIsLessThanTheOffsetTime() {
        assertThat(window.shouldSendNotification(testTimeBeforeArrivalToStartNotifying - 1), is(true));
    }

    @Test
    public void shouldKnowToSendNotificationsWhenTheTimeToArrivalIsTheSameAsTheOffsetTime() {
        assertThat(window.shouldSendNotification(testTimeBeforeArrivalToStartNotifying), is(true));
    }

    @Test
    public void shouldKnowToNotSendNotificationsWhenTheTimeToArrivalIsGreaterThanTheOffsetTime() {
        assertThat(window.shouldSendNotification(testTimeBeforeArrivalToStartNotifying + 1), is(false));
    }
}
