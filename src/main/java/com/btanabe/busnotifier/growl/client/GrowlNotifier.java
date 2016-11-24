package com.btanabe.busnotifier.growl.client;

import com.btanabe.busnotifier.growl.factories.GrowlApplicationInfoFactory;
import com.btanabe.busnotifier.growl.factories.GrowlClientFactory;
import com.btanabe.busnotifier.growl.factories.GrowlNotificationFactory;
import com.btanabe.busnotifier.growl.factories.GrowlNotificationInfoFactory;
import com.btanabe.busnotifier.message.GrowlNotification;
import com.btanabe.busnotifier.message.ShutdownSignal;
import com.google.code.jgntp.GntpApplicationInfo;
import com.google.code.jgntp.GntpClient;
import com.google.code.jgntp.GntpNotification;
import com.google.code.jgntp.GntpNotificationInfo;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by Brian on 11/19/16.
 */
@Slf4j
@RequiredArgsConstructor
public class GrowlNotifier {

    @NonNull
    private AsyncEventBus eventBus;

    @NonNull
    private GrowlNotificationInfoFactory notificationInfoFactory;

    @NonNull
    private GrowlApplicationInfoFactory applicationInfoFactory;

    @NonNull
    private GrowlNotificationFactory notificationFactory;

    @NonNull
    private GrowlClientFactory clientFactory;

    @Subscribe
    public void sendNotification(GrowlNotification growlNotification) throws Exception {
        log.info("Received notification: " + growlNotification);

        GntpApplicationInfo applicationInfo = applicationInfoFactory.createApplicationInfo();
        GntpNotificationInfo notificationInfo = notificationInfoFactory.createNotificationInfo(applicationInfo, growlNotification.getNotificationName());
        GntpClient client = clientFactory.createClient(applicationInfo);
        GntpNotification notification = notificationFactory.createNotification(notificationInfo, growlNotification.getNotificationTitle(), growlNotification.getNotificationText());

        client.register();
        client.notify(notification);
        client.shutdown(0, MILLISECONDS);

        eventBus.post(new ShutdownSignal());
    }
}
