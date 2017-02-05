package com.btanabe.busnotifier.tasks;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.net.URL;

import static com.google.common.base.Charsets.UTF_8;

/**
 * Created by Brian on 4/15/16.
 */
@Slf4j
@AllArgsConstructor
public class WebRequest {

    public static String getPage(String urlToDownload) {
        try {
            return IOUtils.toString(new URL(urlToDownload).openStream(), UTF_8);
        } catch (Exception ex) {
            log.error(String.format("Failed to GET web content due to exception=[%s] stacktrace: %s",
                    ex.getClass().getName(),
                    ExceptionUtils.getStackTrace(ex)));
            throw new RuntimeException(ex);
        }
    }
}
