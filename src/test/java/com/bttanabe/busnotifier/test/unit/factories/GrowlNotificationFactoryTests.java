package com.bttanabe.busnotifier.test.unit.factories;

import com.btanabe.busnotifier.message.internal.AcknowledgedMessage;
import com.btanabe.busnotifier.message.internal.BusArrivalMessage;
import com.btanabe.busnotifier.notifiers.GrowlNotifier;
import com.btanabe.busnotifier.notifiers.growl.factories.GrowlClientFactory;
import com.btanabe.busnotifier.utilities.TimeHelper;
import com.bttanabe.busnotifier.test.utilities.AcknowledgedMessageListener;
import com.bttanabe.busnotifier.test.utilities.ImageComparator;
import com.google.code.jgntp.GntpApplicationInfo;
import com.google.code.jgntp.GntpClient;
import com.google.code.jgntp.GntpNotification;
import com.google.common.eventbus.EventBus;
import org.junit.Before;
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
import static org.mockito.Matchers.any;
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
    private GrowlNotifier notifier;

    @Value("classpath:test-images/expected-route11-icon.png")
    private Resource expectedRoute11Image;

    private AcknowledgedMessageListener messageListener;

    private GntpClient gntpClient;
    private ArgumentCaptor<GntpNotification> notificationArgumentCaptor;

    @Before
    public void createTestGrowlNotificationFactory() {
        messageListener = new AcknowledgedMessageListener();

        eventBus.register(notifier);
        eventBus.register(messageListener);
    }

    @Before
    public void createMockGntpClient() throws Exception {
        gntpClient = mock(GntpClient.class);

        GrowlClientFactory growlClientFactory = mock(GrowlClientFactory.class);
        when(growlClientFactory.createClient(any(GntpApplicationInfo.class))).thenReturn(gntpClient);

        notifier.setClientFactory(growlClientFactory);
    }

    @Before
    public void resetArgumentCaptor() {
        notificationArgumentCaptor = ArgumentCaptor.forClass(GntpNotification.class);
    }

    @Test
    public void shouldBeAbleToPostNotificationsWithTheCorrectTitle() {
        BusArrivalMessage arrivalMessage = postTestMessage(TEST_EXPECTED_ARRIVAL_TIME, TEST_ROUTE_NAME, TEST_ROUTE_LOCATION);
        messageListener.blockUntilAcknowledgeMessageIsReceived(new AcknowledgedMessage(arrivalMessage));

        assertThat(capturePostedNotification().getTitle(), is(equalTo(String.format("Route %s: %d minutes away", arrivalMessage.getRouteName(), TimeHelper.getTimeDifferenceInMinutes(LocalDateTime.now(), arrivalMessage.getExpectedArrivalTime())))));
    }

    @Test
    public void shouldBeAbleToPostNotificationsWithTheCorrectText() {
        BusArrivalMessage arrivalMessage = postTestMessage(TEST_EXPECTED_ARRIVAL_TIME, TEST_ROUTE_NAME, TEST_ROUTE_LOCATION);
        messageListener.blockUntilAcknowledgeMessageIsReceived(new AcknowledgedMessage(arrivalMessage));

        assertThat(capturePostedNotification().getText(), is(equalTo("E Madison St & 25th Ave E")));
    }

    @Test
    public void shouldBeAbleToPostNotificationsWithTheCorrectApplicationName() {
        BusArrivalMessage arrivalMessage = postTestMessage(TEST_EXPECTED_ARRIVAL_TIME, TEST_ROUTE_NAME, TEST_ROUTE_LOCATION);
        messageListener.blockUntilAcknowledgeMessageIsReceived(new AcknowledgedMessage(arrivalMessage));

        assertThat(capturePostedNotification().getApplicationName(), is(equalTo("Bus Notifier")));
    }

    @Test
    public void shouldBeAbleToPostNotificationsWithTheCorrectName() {
        BusArrivalMessage arrivalMessage = postTestMessage(TEST_EXPECTED_ARRIVAL_TIME, TEST_ROUTE_NAME, TEST_ROUTE_LOCATION);
        messageListener.blockUntilAcknowledgeMessageIsReceived(new AcknowledgedMessage(arrivalMessage));

        assertThat(capturePostedNotification().getName(), is(equalTo("Arrival Notification")));
    }

    @Test
    public void shouldBeAbleToPostNotificationsWithTheCorrectIcon() throws Exception {
        BusArrivalMessage arrivalMessage = postTestMessage(TEST_EXPECTED_ARRIVAL_TIME, TEST_ROUTE_NAME, TEST_ROUTE_LOCATION);
        messageListener.blockUntilAcknowledgeMessageIsReceived(new AcknowledgedMessage(arrivalMessage));

        ImageComparator.assertImageEquals(capturePostedNotification().getIconImage(), ImageIO.read(expectedRoute11Image.getFile()));
    }

    private BusArrivalMessage postTestMessage(Long expectedArrivalTime, String routeName, String routeLocation) {
        BusArrivalMessage message = new BusArrivalMessage(getLocalDateTime(expectedArrivalTime), routeName, routeLocation);
        eventBus.post(message);
        return message;
    }

    private GntpNotification capturePostedNotification() {
        verify(gntpClient).notify(notificationArgumentCaptor.capture());
        return notificationArgumentCaptor.getValue();
    }
}
