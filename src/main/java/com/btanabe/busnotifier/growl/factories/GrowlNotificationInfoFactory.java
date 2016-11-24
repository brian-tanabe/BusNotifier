package com.btanabe.busnotifier.growl.factories;

import com.google.code.jgntp.Gntp;
import com.google.code.jgntp.GntpApplicationInfo;
import com.google.code.jgntp.GntpNotificationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Brian on 11/20/16.
 */
@Slf4j
public class GrowlNotificationInfoFactory {

    public static GntpNotificationInfo createNotificationInfo(GntpApplicationInfo applicationInfo, String notificationName) throws InterruptedException {
        GntpNotificationInfo notificationInfo = Gntp.notificationInfo(applicationInfo, notificationName).build();
        applicationInfo.addNotificationInfo(notificationInfo);

        return notificationInfo;
    }
}
