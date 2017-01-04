package com.bttanabe.busnotifier.test.integration.application;

import com.btanabe.busnotifier.application.UpdateController;
import com.btanabe.busnotifier.message.internal.AcknowledgedMessage;
import com.btanabe.busnotifier.message.internal.BusArrivalMessage;
import com.btanabe.busnotifier.message.internal.ShutdownSignal;
import com.bttanabe.busnotifier.test.integration.MockWebRequestBase;
import com.bttanabe.busnotifier.test.utilities.AcknowledgedMessageListener;
import com.bttanabe.busnotifier.test.utilities.BusArrivalMessageListener;
import com.google.common.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 1/2/17.
 */
public class UpdateControllerTests extends MockWebRequestBase {

    @Autowired
    @Qualifier("testUpdateController")
    private UpdateController updateController;

    @Autowired
    @Qualifier("notificationEventBus")
    private EventBus eventBus;

    @Resource(name = "expectedRoute11At25thAndMadisonStList")
    private List<BusArrivalMessage> expectedArrivalMessageList;

    private BusArrivalMessageListener messageListener;

    private AcknowledgedMessageListener acknowledgedMessageListener;

    @Before
    public void setupMessageListener() {
        messageListener = new BusArrivalMessageListener();
        acknowledgedMessageListener = new AcknowledgedMessageListener();

        eventBus.register(messageListener);
        eventBus.register(updateController);
        eventBus.register(acknowledgedMessageListener);
    }

    @Test
    public void shouldBeAbleToPostNotificationForIncomingBuses() throws Exception {
        updateController.startUpdateHeartbeat();

        ShutdownSignal shutdownSignal = new ShutdownSignal(1000L, MILLISECONDS);
        eventBus.post(shutdownSignal);
        acknowledgedMessageListener.blockUntilAcknowledgeMessageIsReceived(new AcknowledgedMessage(shutdownSignal));

        Set<BusArrivalMessage> busArrivalMessages = getUniqueBusArrivalMessages(messageListener.getArrivalMessages());
        assertThat(busArrivalMessages, containsInAnyOrder(expectedArrivalMessageList.toArray()));
    }

    private Set<BusArrivalMessage> getUniqueBusArrivalMessages(List<BusArrivalMessage> busArrivalMessageList) {
        Set<BusArrivalMessage> uniqueMessages = new HashSet<>();
        uniqueMessages.addAll(busArrivalMessageList);
        return uniqueMessages;
    }
}
