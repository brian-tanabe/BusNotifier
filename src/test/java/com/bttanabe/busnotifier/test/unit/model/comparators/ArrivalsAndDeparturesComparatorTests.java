package com.bttanabe.busnotifier.test.unit.model.comparators;

import com.btanabe.busnotifier.model.comparators.ArrivalsAndDeparturesComparator;
import com.btanabe.busnotifier.model.types.ArrivalsAndDepartures;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 12/12/16.
 */
@ContextConfiguration("classpath:spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ArrivalsAndDeparturesComparatorTests {
    private ArrivalsAndDeparturesComparator comparator = new ArrivalsAndDeparturesComparator();

    @Test
    public void shouldBeAbleToCompareTwoLessThanArrivalsAndDeparturesWithoutPredictedDepartureTimes() {
        ArrivalsAndDepartures earlier = new ArrivalsAndDepartures();
        earlier.setPredictedDepartureTime(0L);
        earlier.setScheduledDepartureTime(4L);

        ArrivalsAndDepartures later = new ArrivalsAndDepartures();
        later.setPredictedDepartureTime(0L);
        later.setScheduledDepartureTime(5L);

        assertThat(comparator.compare(earlier, later), is(lessThan(0)));
        assertThat(comparator.compare(later, earlier), is(greaterThan(0)));
    }

    @Test
    public void shouldBeAbleToCompareTwoLessThanArrivalsAndDeparturesWithPredeictedDepartureTimes() {
        ArrivalsAndDepartures earlier = new ArrivalsAndDepartures();
        earlier.setPredictedDepartureTime(4L);
        earlier.setScheduledDepartureTime(4L);

        ArrivalsAndDepartures later = new ArrivalsAndDepartures();
        later.setPredictedDepartureTime(5L);
        later.setScheduledDepartureTime(5L);

        assertThat(comparator.compare(earlier, later), is(lessThan(0)));
        assertThat(comparator.compare(later, earlier), is(greaterThan(0)));
    }

    @Test
    public void shouldBeAbleToCompareTwoArrivalsAndDeparturesWithEarlierPredictedThanScheduledDepartureTime() {
        ArrivalsAndDepartures earlier = new ArrivalsAndDepartures();
        earlier.setPredictedDepartureTime(4L);
        earlier.setScheduledDepartureTime(5L);

        ArrivalsAndDepartures later = new ArrivalsAndDepartures();
        later.setPredictedDepartureTime(5L);
        later.setScheduledDepartureTime(5L);

        assertThat(comparator.compare(earlier, later), is(lessThan(0)));
        assertThat(comparator.compare(later, earlier), is(greaterThan(0)));
    }


    @Test
    public void shouldBeAbleToCompareTwoArrivalsAndDeparturesWithLaterPredictedThanScheduledDepartureTime() {
        ArrivalsAndDepartures earlier = new ArrivalsAndDepartures();
        earlier.setPredictedDepartureTime(5L);
        earlier.setScheduledDepartureTime(5L);

        ArrivalsAndDepartures later = new ArrivalsAndDepartures();
        later.setPredictedDepartureTime(6L);
        later.setScheduledDepartureTime(4L);

        assertThat(comparator.compare(earlier, later), is(lessThan(0)));
        assertThat(comparator.compare(later, earlier), is(greaterThan(0)));
    }

    @Test
    public void shouldBeAbleToDetermineEqualityWhenScheduledDepartureTimesAreNotTheSame() {
        ArrivalsAndDepartures earlier = new ArrivalsAndDepartures();
        earlier.setPredictedDepartureTime(6L);
        earlier.setScheduledDepartureTime(5L);

        ArrivalsAndDepartures later = new ArrivalsAndDepartures();
        later.setPredictedDepartureTime(6L);
        later.setScheduledDepartureTime(4L);

        assertThat(comparator.compare(earlier, later), is(equalTo(0)));
    }

    @Test
    public void shouldBeAbleToDetermineEqualityWhenPredictedTimesAreNotAvailble() {
        ArrivalsAndDepartures earlier = new ArrivalsAndDepartures();
        earlier.setPredictedDepartureTime(0L);
        earlier.setScheduledDepartureTime(5L);

        ArrivalsAndDepartures later = new ArrivalsAndDepartures();
        later.setPredictedDepartureTime(0L);
        later.setScheduledDepartureTime(5L);

        assertThat(comparator.compare(earlier, later), is(equalTo(0)));
    }

    @Test
    public void shouldBeAbleToDetermineEqualityWhenLhsPredictionIsNotAvailble() {
        ArrivalsAndDepartures earlier = new ArrivalsAndDepartures();
        earlier.setPredictedDepartureTime(5L);
        earlier.setScheduledDepartureTime(6L);

        ArrivalsAndDepartures later = new ArrivalsAndDepartures();
        later.setPredictedDepartureTime(0L);
        later.setScheduledDepartureTime(5L);

        assertThat(comparator.compare(earlier, later), is(equalTo(0)));
    }

    @Test
    public void shouldBeAbleToDetermineEqualityWhenRhsPredictionIsNotAvailble() {
        ArrivalsAndDepartures earlier = new ArrivalsAndDepartures();
        earlier.setPredictedDepartureTime(0L);
        earlier.setScheduledDepartureTime(6L);

        ArrivalsAndDepartures later = new ArrivalsAndDepartures();
        later.setPredictedDepartureTime(6L);
        later.setScheduledDepartureTime(5L);

        assertThat(comparator.compare(earlier, later), is(equalTo(0)));
    }
}
