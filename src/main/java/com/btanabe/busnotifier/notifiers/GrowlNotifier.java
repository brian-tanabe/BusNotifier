package com.btanabe.busnotifier.notifiers;

import com.btanabe.busnotifier.message.internal.BusArrivalMessage;
import com.btanabe.busnotifier.notifiers.growl.factories.GrowlApplicationInfoFactory;
import com.btanabe.busnotifier.notifiers.growl.factories.GrowlClientFactory;
import com.btanabe.busnotifier.notifiers.growl.factories.GrowlNotificationFactory;
import com.btanabe.busnotifier.notifiers.growl.factories.GrowlNotificationInfoFactory;
import com.btanabe.busnotifier.utilities.ImageHelper;
import com.btanabe.busnotifier.utilities.TimeHelper;
import com.google.code.jgntp.GntpApplicationInfo;
import com.google.code.jgntp.GntpClient;
import com.google.code.jgntp.GntpNotification;
import com.google.code.jgntp.GntpNotificationInfo;
import com.google.common.eventbus.EventBus;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

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

    @NonNull
    private ImageHelper iconCreator;

    public GrowlNotifier(GrowlNotificationInfoFactory notificationInfoFactory,
                         GrowlApplicationInfoFactory applicationInfoFactory,
                         GrowlNotificationFactory notificationFactory,
                         GrowlClientFactory clientFactory,
                         ImageHelper iconCreator,
                         EventBus eventBus) {

        super(eventBus);
        this.notificationInfoFactory = notificationInfoFactory;
        this.notificationFactory = notificationFactory;
        this.applicationInfoFactory = applicationInfoFactory;
        this.clientFactory = clientFactory;
        this.iconCreator = iconCreator;
    }

    @Override
    protected void postNotification(BusArrivalMessage message) throws Exception {
        log.info("Received notification: " + message);

        GntpApplicationInfo applicationInfo = applicationInfoFactory.createApplicationInfo();
        GntpNotificationInfo notificationInfo = notificationInfoFactory.createNotificationInfo(applicationInfo, NOTIFICATION_NAME);
        GntpClient client = clientFactory.createClient(applicationInfo);
        GntpNotification notification = notificationFactory.createNotification(
                notificationInfo,
                String.format("Route %s: %d minutes away", message.getRouteName(), TimeHelper.getTimeDifferenceInMinutes(LocalDateTime.now(), message.getExpectedArrivalTime())),
                message.getStopLocation(),
                iconCreator.createMessageIcon(message.getRouteName()));

        client.register();
        client.notify(notification);
        client.shutdown(0, MILLISECONDS);
    }
}
