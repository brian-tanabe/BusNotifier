package com.btanabe.busnotifier.message.external;

import java.awt.*;

/**
 * Created by Brian on 11/24/16.
 */
public interface ExternalNotification {

    String getNotificationName();
    String getNotificationTitle();
    String getNotificationText();
    Image getNotificationIcon();
}
