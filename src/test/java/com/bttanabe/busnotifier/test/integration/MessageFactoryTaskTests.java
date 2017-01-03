package com.bttanabe.busnotifier.test.integration;

import com.btanabe.busnotifier.configuration.TravelWindow;
import com.btanabe.busnotifier.message.internal.BusArrivalMessage;
import com.btanabe.busnotifier.onebusaway.ArrivalsAndDeparturesForStopActivity;
import com.btanabe.busnotifier.tasks.MessageFactoryTask;
import com.bttanabe.busnotifier.test.factories.MockTravelWindowFactory;
import com.bttanabe.busnotifier.test.utilities.ModelListener;
import com.google.common.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;

import static com.btanabe.busnotifier.utilities.TimeHelper.getLocalDateTime;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 12/11/16.
 * <p>
 * This class feels like it should have more tests but the single one it has does a ton.
 */
public class MessageFactoryTaskTests extends MockWebRequestBase {

    @Autowired
    @Qualifier("notificationEventBus")
    private EventBus eventBus;

    @Autowired
    @Qualifier("eightToTenThirtyRoute11TravelWindow")
    private TravelWindow testTravelWindow;

    @Autowired
    @Qualifier("testArrivalsAndDeparturesForStopActivity")
    private ArrivalsAndDeparturesForStopActivity activity;

    private ModelListener modelListener;

    @Before
    public void registerModelListener() {
        modelListener = new ModelListener();
        eventBus.register(modelListener);
    }

    /**
     * @throws Throwable
     */
    @Test
    public void shouldBeAbleToDetermineWhenRoutesAreComing() throws Throwable {
        TravelWindow mockTravelWindow = MockTravelWindowFactory.createMockTravelWindow(testTravelWindow);

        new MessageFactoryTask(eventBus, activity, Arrays.asList(mockTravelWindow)).call();

        assertThat(modelListener.getArrivalMessages(), hasItems(createBusArrivalMessage(mockTravelWindow, "11", 1480191995000L), createBusArrivalMessage(mockTravelWindow, "11", 1480193036000L)));
    }

    private BusArrivalMessage createBusArrivalMessage(TravelWindow travelWindow, String routeName, Long arrivalTime) {
        return new BusArrivalMessage(getLocalDateTime(arrivalTime), routeName, travelWindow.getRouteAtStopToMonitor().getStopName());
    }
}
