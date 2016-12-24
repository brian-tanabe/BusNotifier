package com.bttanabe.busnotifier.test.integration;

import com.btanabe.busnotifier.configuration.RouteAtStopToMonitor;
import com.btanabe.busnotifier.configuration.TravelWindow;
import com.btanabe.busnotifier.message.internal.BusArrivalMessage;
import com.btanabe.busnotifier.onebusaway.ArrivalsAndDeparturesForStopActivity;
import com.btanabe.busnotifier.tasks.MessageFactoryTask;
import com.bttanabe.busnotifier.test.utilities.ModelListener;
import com.google.common.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;
import java.util.List;

import static com.bttanabe.busnotifier.test.constants.TestRouteIds.ROUTE_1_100009;
import static com.bttanabe.busnotifier.test.constants.TestStopIds.STOP_1_12340;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 12/11/16.
 *
 * This class feels like it should have more tests but the single one it has does a ton.
 */
public class MessageFactoryTaskTests extends MockWebRequestBase {

    @Autowired
    @Qualifier("notificationEventBus")
    private EventBus eventBus;

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
     * Create a test whose window ends at 1480192055000L.  That'll filter out one of the two routes
     *
     * @throws Throwable
     */
    @Test
    public void shouldBeAbleToDetermineWhenRoutesAreComing() throws Throwable {
        TravelWindow testTravelWindow = new TravelWindow(new RouteAtStopToMonitor(ROUTE_1_100009, STOP_1_12340, "E Madison St & 25th Ave E"), 1480191395000L, 1480193096000L, 600000L);
        MessageFactoryTask notificationFactoryTask = new MessageFactoryTask(eventBus, activity, Arrays.asList(testTravelWindow));
        notificationFactoryTask.call();

        List<BusArrivalMessage> collectedRecords = modelListener.getArrivalMessages();
        assertThat(collectedRecords, hasItems(new BusArrivalMessage(1480191995000L, "11", "E Madison St & 25th Ave E"), new BusArrivalMessage(1480193036000L, "11", "E Madison St & 25th Ave E")));
    }
}
