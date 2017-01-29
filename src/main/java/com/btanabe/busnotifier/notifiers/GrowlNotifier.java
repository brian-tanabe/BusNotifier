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
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Brian on 11/19/16.
 */
@Slf4j
public class GrowlNotifier extends AbstractNotifier {

    private static final String NOTIFICATION_NAME = "Arrival Notification";

    @NonNull
    @Setter(onMethod = @__({@Autowired}))
    private GrowlNotificationInfoFactory notificationInfoFactory;

    @NonNull
    @Setter(onMethod = @__({@Autowired}))
    private GrowlApplicationInfoFactory applicationInfoFactory;

    @NonNull
    @Setter(onMethod = @__({@Autowired}))
    private GrowlNotificationFactory notificationFactory;

    @NonNull
    @Setter(onMethod = @__({@Autowired}))
    private GrowlClientFactory clientFactory;

    @NonNull
    @Setter(onMethod = @__({@Autowired}))
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
        GntpNotificationInfo notificationInfo = GrowlNotificationInfoFactory.createNotificationInfo(applicationInfo, NOTIFICATION_NAME);
        GntpClient client = clientFactory.createClient(applicationInfo);
        GntpNotification notification = notificationFactory.createNotification(
                notificationInfo,
                String.format("Route %s: %d minutes away", message.getRouteName(), TimeHelper.getTimeDifferenceInMinutes(LocalDateTime.now(), message.getExpectedArrivalTime())),
                message.getStopLocation(),
                iconCreator.createMessageIcon(message.getRouteName()));

        client.register();
        client.waitRegistration();

        boolean isNotificationPostedSuccessfully = client.notify(notification, 30, SECONDS);

        client.shutdown(0, MILLISECONDS);
        boolean isClientShutdown = client.isShutdown();

        log.info(String.format("Notification successful=[%s], Shutdown successful=[%s]", isNotificationPostedSuccessfully, isClientShutdown));
    }
}
