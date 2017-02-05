package com.btanabe.busnotifier.utilities.images;

import fr.jcgay.notification.Icon;

import java.io.IOException;

/**
 * Created by Brian on 2/4/17.
 */
public class ApplicationIconFactory extends ImageHelper {
    private static final String BASE_IMAGE = "images/oba_logo.png";
    private static final String APPLICATION_NAME = "Bus Notifier";

    public Icon createApplicationIconIcon() throws IOException {
        return Icon.create(getFilesystemUrlForBufferedImage(getImageAsBufferedImage(BASE_IMAGE)), APPLICATION_NAME);
    }
}
