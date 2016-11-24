package com.btanabe.busnotifier.message;

import java.awt.*;

/**
 * Created by Brian on 11/24/16.
 */
public interface Notification {

    String getNotificationName();
    String getNotificationTitle();
    String getNotificationText();
    Image getNotificationIcon();
}
