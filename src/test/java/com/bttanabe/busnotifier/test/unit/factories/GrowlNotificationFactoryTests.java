package com.bttanabe.busnotifier.test.unit.factories;

import com.btanabe.busnotifier.message.internal.AcknowledgedMessage;
import com.btanabe.busnotifier.message.internal.BusArrivalMessage;
import com.btanabe.busnotifier.notifiers.GrowlNotifier;
import com.btanabe.busnotifier.notifiers.notifier.NotifierFactory;
import com.btanabe.busnotifier.utilities.TimeHelper;
import com.bttanabe.busnotifier.test.utilities.AcknowledgedMessageListener;
import com.bttanabe.busnotifier.test.utilities.ImageComparator;
import com.google.common.eventbus.EventBus;
import fr.jcgay.notification.Notification;
import fr.jcgay.notification.Notifier;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.imageio.ImageIO;
import java.time.LocalDateTime;

import static com.btanabe.busnotifier.utilities.TimeHelper.getLocalDateTime;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Brian on 12/23/16.
 */
@ContextConfiguration("classpath:spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GrowlNotificationFactoryTests {

    private static final Long TEST_EXPECTED_ARRIVAL_TIME = 1480191995000L;
    private static final String TEST_ROUTE_NAME = "11";
    private static final String TEST_ROUTE_LOCATION = "E Madison St & 25th Ave E";

    @Autowired
    @Qualifier("notificationEventBus")
    private EventBus eventBus;

    @Autowired
    @Qualifier("growlNotifier")
    private GrowlNotifier notifierNotifier;

    @Value("classpath:test-images/expected-route11-icon.png")
    private Resource expectedRoute11Image;

    private AcknowledgedMessageListener messageListener;

    private Notifier notifier;
    private ArgumentCaptor<Notification> notificationArgumentCaptor;

    @Before
    public void createTestGrowlNotificationFactory() {
        messageListener = new AcknowledgedMessageListener();

        eventBus.register(notifierNotifier);
        eventBus.register(messageListener);
    }

    @Before
    public void createMockNotifier() throws Exception {
        notifier = mock(Notifier.class);

        NotifierFactory notifierFactory = mock(NotifierFactory.class);
        when(notifierFactory.createNotifier()).thenReturn(notifier);

        notifierNotifier.setNotifierFactory(notifierFactory);
    }

    @Before
    public void resetNotificationArgumentCaptor() {
        notificationArgumentCaptor = ArgumentCaptor.forClass(Notification.class);
    }

    @Test
    public void shouldBeAbleToPostNotificationsWithTheCorrectTitle() throws Exception {
        BusArrivalMessage arrivalMessage = postTestMessage(TEST_EXPECTED_ARRIVAL_TIME, TEST_ROUTE_NAME, TEST_ROUTE_LOCATION);
        messageListener.blockUntilAcknowledgeMessageIsReceived(new AcknowledgedMessage(arrivalMessage));

        assertThat(captureNotification().title(), is(equalTo(String.format("Route %s: %d minutes away", arrivalMessage.getRouteName(), TimeHelper.getTimeDifferenceInMinutes(LocalDateTime.now(), arrivalMessage.getExpectedArrivalTime())))));
    }

    @Test
    public void shouldBeAbleToPostNotificationsWithTheCorrectText() throws Exception {
        BusArrivalMessage arrivalMessage = postTestMessage(TEST_EXPECTED_ARRIVAL_TIME, TEST_ROUTE_NAME, TEST_ROUTE_LOCATION);
        messageListener.blockUntilAcknowledgeMessageIsReceived(new AcknowledgedMessage(arrivalMessage));

        assertThat(captureNotification().message(), is(equalTo("E Madison St & 25th Ave E")));
    }

    // TODO FIGURE OUT A WAY TO TEST THIS:
    @Test
    @Ignore
    public void shouldBeAbleToPostNotificationsWithTheCorrectApplicationName() throws Exception {
        BusArrivalMessage arrivalMessage = postTestMessage(TEST_EXPECTED_ARRIVAL_TIME, TEST_ROUTE_NAME, TEST_ROUTE_LOCATION);
        messageListener.blockUntilAcknowledgeMessageIsReceived(new AcknowledgedMessage(arrivalMessage));

        // Application name:
        assertThat(captureNotification().title(), is(equalTo("Bus Notifier")));
    }

    // TODO FIGURE OUT A WAY TO TEST THIS:
    @Test
    @Ignore
    public void shouldBeAbleToPostNotificationsWithTheCorrectName() throws Exception {
        BusArrivalMessage arrivalMessage = postTestMessage(TEST_EXPECTED_ARRIVAL_TIME, TEST_ROUTE_NAME, TEST_ROUTE_LOCATION);
        messageListener.blockUntilAcknowledgeMessageIsReceived(new AcknowledgedMessage(arrivalMessage));

        // Name:
        assertThat(captureNotification().message(), is(equalTo("Arrival Notification")));
    }

    @Test
    public void shouldBeAbleToPostNotificationsWithTheCorrectIcon() throws Exception {
        BusArrivalMessage arrivalMessage = postTestMessage(TEST_EXPECTED_ARRIVAL_TIME, TEST_ROUTE_NAME, TEST_ROUTE_LOCATION);
        messageListener.blockUntilAcknowledgeMessageIsReceived(new AcknowledgedMessage(arrivalMessage));

        ImageComparator.assertImageEquals(captureNotification().icon().toRenderedImage(), ImageIO.read(expectedRoute11Image.getFile()));
    }

    private BusArrivalMessage postTestMessage(Long expectedArrivalTime, String routeName, String routeLocation) {
        BusArrivalMessage message = new BusArrivalMessage(getLocalDateTime(expectedArrivalTime), routeName, routeLocation);
        eventBus.post(message);
        return message;
    }

    private Notification captureNotification() throws Exception {
        verify(notifier).send(notificationArgumentCaptor.capture());
        verify(notifier).close();
        return notificationArgumentCaptor.getValue();
    }
}
