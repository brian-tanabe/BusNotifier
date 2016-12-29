package com.btanabe.busnotifier.configuration.providers;

import com.btanabe.busnotifier.configuration.ApplicationConfiguration;
import com.btanabe.busnotifier.configuration.TravelWindow;

import java.util.List;

/**
 * Created by Brian on 12/26/16.
 */
public interface ConfigurationProvider {
    List<TravelWindow> getTravelWindowsToMonitor() throws Exception;

    ApplicationConfiguration getApplicationConfiguration() throws Exception;
}
