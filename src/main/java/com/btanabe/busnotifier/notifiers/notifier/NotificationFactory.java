package com.btanabe.busnotifier.notifiers.notifier;

import com.btanabe.busnotifier.message.internal.BusArrivalMessage;
import com.btanabe.busnotifier.utilities.TimeHelper;
import com.btanabe.busnotifier.utilities.images.NotificationIconFactory;
import fr.jcgay.notification.Notification;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by Brian on 2/5/17.
 */
public class NotificationFactory {
    private static NotificationIconFactory notificationIconFactory = new NotificationIconFactory();

    public static Notification createNotification(BusArrivalMessage message) throws IOException {
        Notification notification = Notification.builder()
                .level(Notification.Level.INFO)
                .message(message.getStopLocation())
                .title(String.format("Route %s: %d minutes away", message.getRouteName(), TimeHelper.getTimeDifferenceInMinutes(LocalDateTime.now(), message.getExpectedArrivalTime())))
                .icon(notificationIconFactory.createMessageIcon(message.getRouteName()))
                .build();

        return notification;
    }
}
