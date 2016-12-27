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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.btanabe.busnotifier.utilities.TimeHelper.getLocalDateTime;
import static com.bttanabe.busnotifier.test.constants.TestRouteIds.ROUTE_1_100009;
import static com.bttanabe.busnotifier.test.constants.TestStopIds.STOP_1_12340;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        TravelWindow mockTravelWindow = mock(TravelWindow.class);
        when(mockTravelWindow.getRouteAtStopToMonitor()).thenReturn(new RouteAtStopToMonitor(ROUTE_1_100009, STOP_1_12340, "E Madison St & 25th Ave E"));
        when(mockTravelWindow.getMinutesBeforeArrivalToStartNotifying()).thenReturn(12L);
        when(mockTravelWindow.isTimeWithinWindow(any(LocalDateTime.class))).thenReturn(true);
        when(mockTravelWindow.shouldSendNotification(any(Long.class))).thenReturn(true);

        MessageFactoryTask notificationFactoryTask = new MessageFactoryTask(eventBus, activity, Arrays.asList(mockTravelWindow));
        notificationFactoryTask.call();

        List<BusArrivalMessage> collectedRecords = modelListener.getArrivalMessages();
        assertThat(collectedRecords, hasItems(new BusArrivalMessage(getLocalDateTime(1480191995000L), "11", "E Madison St & 25th Ave E"), new BusArrivalMessage(getLocalDateTime(1480193036000L), "11", "E Madison St & 25th Ave E")));
    }
}
