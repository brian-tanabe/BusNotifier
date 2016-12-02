package com.btanabe.busnotifier.tasks;

import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;

import java.net.URL;

import static com.google.common.base.Charsets.UTF_8;

/**
 * Created by Brian on 4/15/16.
 */
@AllArgsConstructor
public class WebRequest {

    public static String getPage(String urlToDownload) {
        try {
            return IOUtils.toString(new URL(urlToDownload).openStream(), UTF_8);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
