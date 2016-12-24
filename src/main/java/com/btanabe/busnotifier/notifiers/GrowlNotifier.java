package com.btanabe.busnotifier.notifiers;

import com.btanabe.busnotifier.growl.factories.GrowlApplicationInfoFactory;
import com.btanabe.busnotifier.growl.factories.GrowlClientFactory;
import com.btanabe.busnotifier.growl.factories.GrowlNotificationFactory;
import com.btanabe.busnotifier.growl.factories.GrowlNotificationInfoFactory;
import com.btanabe.busnotifier.message.internal.BusArrivalMessage;
import com.btanabe.busnotifier.utilities.TimeHelper;
import com.google.code.jgntp.GntpApplicationInfo;
import com.google.code.jgntp.GntpClient;
import com.google.code.jgntp.GntpNotification;
import com.google.code.jgntp.GntpNotificationInfo;
import com.google.common.eventbus.EventBus;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by Brian on 11/19/16.
 * <p>
 * TODO
 * 1) Clean up that message, it's incorrect. (x)
 * 2) Add images to this
 * 3) Wire into the Application class
 */
@Slf4j
@Setter
public class GrowlNotifier extends AbstractNotifier {

    private static final String NOTIFICATION_NAME = "Arrival Notification";

    @NonNull
    private GrowlNotificationInfoFactory notificationInfoFactory;

    @NonNull
    private GrowlApplicationInfoFactory applicationInfoFactory;

    @NonNull
    private GrowlNotificationFactory notificationFactory;

    @NonNull
    private GrowlClientFactory clientFactory;

    public GrowlNotifier(GrowlNotificationInfoFactory notificationInfoFactory,
                         GrowlApplicationInfoFactory applicationInfoFactory,
                         GrowlNotificationFactory notificationFactory,
                         GrowlClientFactory clientFactory,
                         EventBus eventBus) {

        super(eventBus);
        this.notificationInfoFactory = notificationInfoFactory;
        this.notificationFactory = notificationFactory;
        this.applicationInfoFactory = applicationInfoFactory;
        this.clientFactory = clientFactory;
    }

    @Override
    protected void postNotification(BusArrivalMessage message) throws Exception {
        log.info("Received notification: " + message);

        GntpApplicationInfo applicationInfo = applicationInfoFactory.createApplicationInfo();
        GntpNotificationInfo notificationInfo = notificationInfoFactory.createNotificationInfo(applicationInfo, NOTIFICATION_NAME);
        GntpClient client = clientFactory.createClient(applicationInfo);
        GntpNotification notification = notificationFactory.createNotification(notificationInfo, String.format("Route %s: %d minutes away", message.getRouteName(), TimeHelper.getTimeDifferenceInMinutes(System.currentTimeMillis(), message.getExpectedArrivalTime())), message.getStopLocation());

        client.register();
        client.notify(notification);
        client.shutdown(0, MILLISECONDS);
    }
}
