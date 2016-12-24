package com.btanabe.busnotifier.notifiers.growl.factories;

import com.google.code.jgntp.Gntp;
import com.google.code.jgntp.GntpNotification;
import com.google.code.jgntp.GntpNotificationInfo;

/**
 * Created by Brian on 11/24/16.
 */
public class GrowlNotificationFactory {
    private static final String HEADER_SUFFIX = "bus-notifier";

    public GntpNotification createNotification(GntpNotificationInfo notificationInfo, String notificationTitle, String notificationText) throws InterruptedException {
        return Gntp.notification(notificationInfo, notificationTitle).withCallback().context(notificationInfo.getName().hashCode()).header(Gntp.APP_SPECIFIC_HEADER_PREFIX + HEADER_SUFFIX, "route-11").text(notificationText).build();
    }
}