package com.btanabe.busnotifier.message.external;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.awt.*;

/**
 * Created by Brian on 11/24/16.
 */
@ToString
@AllArgsConstructor
public class GrowlNotification implements ExternalNotification {
    private String notificationName;
    private String notificationTitle;
    private String notificationText;
    private Image notificationImage;

    // "incoming-bus-route-11-convention-place-east"
    @Override
    public String getNotificationName() {
        return notificationName;
    }

    @Override
    public String getNotificationTitle() {
        return notificationTitle;
    }

    @Override
    public String getNotificationText() {
        return notificationText;
    }

    @Override
    public Image getNotificationIcon() {
        return notificationImage;
    }
}
